package com.deledzis.localshare.presentation.screens.forgetpassword

import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.entity.Entity
import com.deledzis.localshare.domain.model.response.auth.ForgetPasswordResponse
import com.deledzis.localshare.domain.model.request.auth.ForgetPasswordRequest
import com.deledzis.localshare.domain.usecase.auth.ForgetPasswordUseCase
import com.deledzis.localshare.presentation.base.BaseViewModel
import kotlinx.coroutines.channels.ReceiveChannel
import javax.inject.Inject

class ForgetPasswordViewModel @Inject constructor(
    private val forgetPasswordUseCase: ForgetPasswordUseCase
) : BaseViewModel() {

    override val receiveChannel: ReceiveChannel<Response<Entity, Error>>
        get() = forgetPasswordUseCase.receiveChannel

    private var _error = MutableLiveData<String>()
    val error = _error

    private var _emailError = MutableLiveData<String>()
    val emailError = _emailError

    private var _email = MutableLiveData<String>()
    val email = _email

    private var _result = MutableLiveData<Boolean>()
    val result = _result

    override suspend fun resolve(value: Response<Entity, Error>) {
        value.handleResult(
            stateBlock = ::handleState,
            failureBlock = ::handleFailure,
            successBlock = ::handleSuccess
        )
    }

    private suspend fun handleSuccess(data: Any?) {
        if (data !is ForgetPasswordResponse) return

        _result.postValue(data.result)
    }

    private suspend fun handleFailure(error: Error) {
        _error.postValue(error.exception?.message)
    }

    private suspend fun handleState(state: Response.State) {
        when (state) {
            is Response.State.Loading -> {
                startLoading()
            }
            is Response.State.Loaded -> {
                stopLoading()
            }
        }
    }

    fun forgetPassword() {
        _error.value = null
        _emailError.value = null

        if (email.value.isNullOrBlank()) {
            _emailError.postValue("Введите E-mail")
            return
        }

        forgetPasswordUseCase(
            ForgetPasswordRequest(
                email = email.value!!
            )
        )
    }
}