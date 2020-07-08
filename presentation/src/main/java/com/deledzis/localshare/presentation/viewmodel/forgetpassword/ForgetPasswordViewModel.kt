package com.deledzis.localshare.presentation.viewmodel.forgetpassword

import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.domain.repository.IForgetPasswordRepository
import com.deledzis.localshare.presentation.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForgetPasswordViewModel @Inject constructor(
    private val repository: IForgetPasswordRepository
) : BaseViewModel() {

    private var _error = MutableLiveData<String>()
    val error = _error

    private var _emailError = MutableLiveData<String>()
    val emailError = _emailError

    private var _email = MutableLiveData<String>()
    val email = _email

    private var _result = MutableLiveData<Boolean>()
    val result = _result

    fun forgetPassword() {
        _error.value = null
        _emailError.value = null

        startLoading()
        scope.launch {
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
        }
    }
}