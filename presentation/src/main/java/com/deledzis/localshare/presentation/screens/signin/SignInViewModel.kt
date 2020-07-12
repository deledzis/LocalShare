package com.deledzis.localshare.presentation.screens.signin

import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.Auth
import com.deledzis.localshare.domain.model.User
import com.deledzis.localshare.domain.model.request.AuthUserRequest
import com.deledzis.localshare.domain.usecase.auth.AuthUserUseCase
import com.deledzis.localshare.domain.usecase.auth.GetUserUseCase
import com.deledzis.localshare.infrastructure.util.isDebug
import com.deledzis.localshare.presentation.base.BaseViewModel
import kotlinx.coroutines.channels.ReceiveChannel
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val authUserUseCase: AuthUserUseCase,
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel() {

    override val receiveChannel: ReceiveChannel<Response<*, Error>>
        get() = authUserUseCase.receiveChannel

    private var _error = MutableLiveData<String>()
    val error = _error

    private var _token = MutableLiveData<String>()

    private var _emailError = MutableLiveData<String>()
    val emailError = _emailError

    private var _passwordError = MutableLiveData<String>()
    val passwordError = _passwordError

    override suspend fun resolve(value: Response<*, Error>) {
        value.handleResult(
            stateBlock = ::handleState,
            failureBlock = ::handleFailure,
            successBlock = ::handleSuccess
        )
    }

    private suspend fun handleSuccess(data: Any?) {
        data ?: handleAuthEmptyResponse()
        if (data is Auth) {
            _token.postValue(data.token)
            getUserUseCase(data.userId)
        }
        if (data is User) {
            data.token = _token.value
            _user.postValue(data)
        }
    }

    private suspend fun handleFailure(error: Error) {
        _error.postValue(error.exception?.message)
        handleAuthEmptyResponse()
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

    private var _email = MutableLiveData<String>()
    val email = _email

    private var _password = MutableLiveData<String>()
    val password = _password

    private var _user = MutableLiveData<User>()
    val user = _user

    fun login() {
        _error.value = null
        _emailError.value = null
        _passwordError.value = null

        if (email.value.isNullOrBlank()) {
            _emailError.postValue("Введите E-mail")
            stopLoading()
            return
        }
        if (password.value.isNullOrBlank()) {
            _passwordError.postValue("Введите пароль")
            stopLoading()
            return
        }
        authUserUseCase(params = AuthUserRequest(
            email = _email.value!!,
            password = password.value!!
        ))
    }

    fun handleFragmentChange() {
        stopLoading()
    }

    private fun handleAuthEmptyResponse() {
        if (isDebug) {
            val mockUser = generateMockUser()
            _user.postValue(mockUser)
        } else {
            _error.postValue("Не удалось войти с предоставленными учетными данными")
        }
    }

    private fun generateMockUser(): User = User(
        id = 1,
        firstName = "Name",
        lastName = "Test",
        token = "asdksadjlkasdjalsd"
    )
}