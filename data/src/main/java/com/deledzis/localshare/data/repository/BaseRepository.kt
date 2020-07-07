package com.deledzis.localshare.data.repository

import com.deledzis.localshare.common.BaseNetworkManager
import com.deledzis.localshare.data.source.server.ApiRemote
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

open class BaseRepository @Inject constructor(
    protected val apiRemote: ApiRemote,
    private val networkManager: BaseNetworkManager
) {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {
        val result: Result<T> = safeApiResult(call, errorMessage)
        var data: T? = null
        when (result) {
            is Result.Success -> data = result.data
            is Result.Error -> {
                if (result.exception is NoInternetConnectionException) {
                    throw NoInternetConnectionException()
                }
                if (result.exception is TestTimeBetweenAttemptsNotGoneException) {
                    throw TestTimeBetweenAttemptsNotGoneException()
                }
                throw result.exception
            }
        }
        return data
    }

    private suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): Result<T> {
        if (networkManager.isConnectedToInternet()) {
            val response = call.invoke()

            if (response.isSuccessful) {
                return Result.Success(response.body()!!)
            }

            if (response.code() == 400 && response.raw().request.url.encodedPath.contains("tests/attempts")) {
                return Result.Error(TestTimeBetweenAttemptsNotGoneException())
            }

            return Result.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
        } else {
            return Result.Error(NoInternetConnectionException())
        }
    }

    class NoInternetConnectionException : Exception()
    class TestTimeBetweenAttemptsNotGoneException : Exception() {
        override val message: String?
            get() = "Время между попытками еще не прошло"
    }
}

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}