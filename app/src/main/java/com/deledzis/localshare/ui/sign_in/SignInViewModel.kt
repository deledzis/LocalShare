package com.deledzis.localshare.ui.sign_in

import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.App
import com.deledzis.localshare.base.BaseViewModel
import com.deledzis.localshare.data.model.auth.Auth
import com.deledzis.localshare.data.model.user.User
import kotlinx.coroutines.launch

class SignInViewModel : BaseViewModel() {
    override val repository: SignInRepository = SignInRepository(App.injector.api())

    private var _error = MutableLiveData<String>()
    val error = _error

    private var _userData = MutableLiveData<Auth>()
    val userData = _userData

    private var _employee = MutableLiveData<User>()
    val employee = _employee

    var email = MutableLiveData("")
    var password = MutableLiveData("")

    fun login() {
        startLoading()
        scope.launch {
            if (email.value.isNullOrBlank() || password.value.isNullOrBlank()) {
                _error.postValue("Нужно ввести E-mail и пароль")
                return@launch
            }
            val userData = repository.login(email.value!!, password.value!!)
            if (userData == null) {
                _error.postValue("Не удалось войти с предоставленными учетными данными")
            } else {
                _userData.postValue(userData)
                val employee = repository.userDetails(userData.userId)
                if (employee == null) {
                    _error.postValue("Не удалось получить сведения о пользователе. Попробуйте позже")
                } else {
                    _employee.postValue(employee)
                }
            }
            stopLoading()
        }
    }
}