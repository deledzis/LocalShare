package com.deledzis.localshare.presentation.screens.forgetpassword

import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.usecase.locationpassword.GetLocationPasswordsUseCase
import com.deledzis.localshare.presentation.base.BaseViewModel
import kotlinx.coroutines.channels.ReceiveChannel
import javax.inject.Inject

class ForgetPasswordViewModel @Inject constructor(
//    private val repository: ForgetPasswordRepository
    private val getLocationPasswordsUseCase: GetLocationPasswordsUseCase
) : BaseViewModel() {

    override val receiveChannel: ReceiveChannel<Response<*, Error>>
        get() = getLocationPasswordsUseCase.receiveChannel

    private var _error = MutableLiveData<String>()
    val error = _error

    private var _emailError = MutableLiveData<String>()
    val emailError = _emailError

    private var _email = MutableLiveData<String>()
    val email = _email

    init {
        getLocationPasswordsUseCase(params = 0) // TODO: User Id
    }

    override suspend fun resolve(value: Response<*, Error>) {
        value.handleResult(
            stateBlock = ::handleState,
            failureBlock = ::handleFailure,
            successBlock = ::handleSuccess
        )
    }

    suspend fun handleSuccess(data: Any?) {
        TODO("Not implemented")
    }

    suspend fun handleFailure(error: Error) {
        TODO("Not implemented")
    }

    suspend fun handleState(state: Response.State) {
        TODO("Not implemented")
    }

    private var _result = MutableLiveData<Boolean>()
    val result = _result

    fun forgetPassword() {
        _error.value = null
        _emailError.value = null

        startLoading()
        /*scope.launch {
            if (email.value.isNullOrBlank()) {
                _emailError.postValue("Введите E-mail")
                stopLoading()
                return@launch
            }

            delay(1000)
            val result = repository.forgetPassword(
                email = email.value!!
            )
            _result.postValue(result)

            stopLoading()
        }*/
    }
}