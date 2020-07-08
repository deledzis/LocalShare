package com.deledzis.localshare.presentation.viewmodel.signin

import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.domain.model.User
import com.deledzis.localshare.domain.repository.ISignInRepository
import com.deledzis.localshare.infrastructure.util.isDebug
import com.deledzis.localshare.presentation.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val repository: ISignInRepository
) : BaseViewModel() {

    private var _error = MutableLiveData<String>()
    val error = _error

    private var _emailError = MutableLiveData<String>()
    val emailError = _emailError

    private var _passwordError = MutableLiveData<String>()
    val passwordError = _passwordError

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

        startLoading()
        scope.launch {
            if (email.value.isNullOrBlank()) {
                _emailError.postValue("Введите E-mail")
                stopLoading()
                return@launch
            }
            if (password.value.isNullOrBlank()) {
                _passwordError.postValue("Введите пароль")
                stopLoading()
                return@launch
            }

            delay(1000)
            val auth = repository.auth(
                email = email.value!!,
                password = password.value!!
            )
            if (auth == null) {
                handleAuthEmptyResponse()
            } else {
                val user = repository.getUser(auth.userId)
                if (user == null) {
                    handleUserEmptyResponse()
                } else {
                    _user.postValue(user)
                }
            }

            stopLoading()
        }
    }

    private fun handleAuthEmptyResponse() {
        if (isDebug) {
            val mockUser = generateMockUser()
            _user.postValue(mockUser)
        } else {
            _error.postValue("Не удалось войти с предоставленными учетными данными")
        }
    }

    private fun handleUserEmptyResponse() {
        if (isDebug) {
            val mockUser = generateMockUser()
            _user.postValue(mockUser)
        } else {
            _error.postValue("Не удалось получить сведения о пользователе. Попробуйте позже")
        }
    }

    private fun generateMockUser(): User = User(
        id = 1,
        firstName = "Name",
        lastName = "Test"
    )
}