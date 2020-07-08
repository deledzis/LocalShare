package com.deledzis.localshare.presentation.viewmodel.register

import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.domain.model.User
import com.deledzis.localshare.domain.repository.IRegisterRepository
import com.deledzis.localshare.infrastructure.util.isDebug
import com.deledzis.localshare.presentation.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val repository: IRegisterRepository
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

    fun register() {
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
            val auth = repository.register(
                email = email.value!!,
                password = password.value!!
            )
            if (auth == null) {
                _error.postValue("Пользователь с таким именем уже существует")
            } else {
                val user = repository.getUser(auth.userId)
                if (user == null) {
                    if (isDebug) {
                        val mockUser = User(
                            id = 1,
                            firstName = "Name",
                            lastName = "Test"
                        )
                        _user.postValue(mockUser)
                    } else {
                        _error.postValue("Не удалось получить сведения о пользователе. Попробуйте позже")
                    }
                } else {
                    _user.postValue(user)
                }
            }

            stopLoading()
        }
    }
}