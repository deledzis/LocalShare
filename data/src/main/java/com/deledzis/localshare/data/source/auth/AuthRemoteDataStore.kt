package com.deledzis.localshare.data.source.auth

import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.data.model.AuthEntity
import com.deledzis.localshare.data.model.UserEntity
import com.deledzis.localshare.data.repository.auth.AuthDataStore
import com.deledzis.localshare.data.repository.auth.AuthRemote
import com.deledzis.localshare.data.repository.locationpasswords.LocationPasswordsDataStore
import javax.inject.Inject

/**
 * Implementation of the [LocationPasswordsDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class AuthRemoteDataStore @Inject constructor(private val authRemote: AuthRemote) :
    AuthDataStore {

    override suspend fun authUser(email: String, password: String): Response<AuthEntity, Error> {
        return try {
            val authResponse = authRemote.auth(
                email = email,
                password = password
            )
            Response.Success(successData = authResponse)
        } catch (e: Exception) {
            Response.Failure(Error.NetworkError(exception = e))
        }
    }

    override suspend fun verifyToken(token: String): Response<AuthEntity, Error> {
        return try {
            val verifyTokenResponse = authRemote.verifyToken(
                token = token
            )
            Response.Success(successData = verifyTokenResponse)
        } catch (e: Exception) {
            Response.Failure(Error.NetworkError(exception = e))
        }
    }

    override suspend fun refreshToken(token: String): Response<AuthEntity, Error> {
        return try {
            val refreshTokenResponse = authRemote.verifyToken(
                token = token
            )
            Response.Success(successData = refreshTokenResponse)
        } catch (e: Exception) {
            Response.Failure(Error.NetworkError(exception = e))
        }
    }

    override suspend fun register(email: String, password: String): Response<AuthEntity, Error> {
        return try {
            val registerResponse = authRemote.register(
                email = email,
                password = password
            )
            Response.Success(successData = registerResponse)
        } catch (e: Exception) {
            Response.Failure(Error.NetworkError(exception = e))
        }
    }

    override suspend fun recoverPassword(email: String): Response<Boolean, Error> {
        return try {
            val recoverPasswordRequestResponse = authRemote.recoverPassword(email = email)
            Response.Success(successData = recoverPasswordRequestResponse)
        } catch (e: Exception) {
            Response.Failure(Error.NetworkError(exception = e))
        }
    }

    override suspend fun getUser(id: Int): Response<UserEntity, Error> {
        return try {
            val userEntityFromRemote = authRemote.getUser(id = id)
            Response.Success(successData = userEntityFromRemote)
        } catch (e: Exception) {
            Response.Failure(Error.NetworkError(exception = e))
        }
    }

}