package com.deledzis.localshare.presentation.screens.signin

import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.BaseUserData
import com.deledzis.localshare.domain.model.User
import com.deledzis.localshare.domain.model.entity.Entity
import com.deledzis.localshare.domain.model.entity.auth.AuthResponse
import com.deledzis.localshare.domain.model.entity.auth.GetUserResponse
import com.deledzis.localshare.domain.model.request.AuthUserRequest
import com.deledzis.localshare.domain.usecase.auth.AuthUserUseCase
import com.deledzis.localshare.domain.usecase.auth.GetUserUseCase
import com.deledzis.localshare.infrastructure.extensions.mergeChannels
import com.deledzis.localshare.infrastructure.util.isDebug
import com.deledzis.localshare.presentation.base.BaseViewModel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val authUserUseCase: AuthUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val userData: BaseUserData
) : BaseViewModel() {

    override val receiveChannel: ReceiveChannel<Response<Entity, Error>>
        get() = mergeChannels(
            authUserUseCase.receiveChannel,
            getUserUseCase.receiveChannel
        )

    private var _error = MutableLiveData<String>()
    val error = _error

    private var _emailError = MutableLiveData<String>()
    val emailError = _emailError

    private var _passwordError = MutableLiveData<String>()
    val passwordError = _passwordError

    private var _inTransition = MutableLiveData<Boolean>()
    val inTransition = _inTransition

    private var _email = MutableLiveData<String>()
    val email = _email

    private var _password = MutableLiveData<String>()
    val password = _password

    private var _token = MutableLiveData<String>()

    private var _user = MutableLiveData<User>(userData.getUser())
    val user = _user

    override suspend fun resolve(value: Response<Entity, Error>) {
        value.handleResult(
            stateBlock = ::handleState,
            failureBlock = ::handleFailure,
            successBlock = ::handleSuccess
        )
    }

    private suspend fun handleSuccess(data: Any?) {
        if (data !is Entity) {
            handleAuthEmptyResponse(_email.value!!)
            return
        }

        if (data is AuthResponse) {
            _token.postValue(data.auth.token)
            getUserUseCase(data.auth.userId)
        }
        if (data is GetUserResponse) {
            data.user.token = _token.value
            _user.postValue(data.user)
        }
    }

    private suspend fun handleFailure(error: Error) {
        _error.postValue(error.exception?.message)
        handleAuthEmptyResponse(_email.value!!)
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
        authUserUseCase(
            params = AuthUserRequest(
                email = _email.value!!,
                password = password.value!!
            )
        )
    }

    fun handleFragmentChange() {
        launch {
            stopLoading()
            _inTransition.postValue(true)
            delay(500)
            _inTransition.postValue(false)
        }
    }

    private fun handleAuthEmptyResponse(email: String) {
        if (isDebug) {
            val mockUser = generateMockUser(email = email)
            _user.postValue(mockUser)
        } else {
            _error.postValue("Не удалось войти с предоставленными учетными данными")
        }
    }

    private fun generateMockUser(email: String): User = User(
        id = 1,
        email = email,
        token = "asdksadjlkasdjalsd"
    )
}