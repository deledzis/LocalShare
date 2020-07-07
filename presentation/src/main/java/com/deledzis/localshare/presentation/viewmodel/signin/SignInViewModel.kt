package com.deledzis.localshare.presentation.viewmodel.signin

import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.domain.repository.ISignInRepository
import com.deledzis.localshare.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val repository: ISignInRepository
) : BaseViewModel() {

    private var _error = MutableLiveData<String>()
    val error = _error

    var email = MutableLiveData("")
    var password = MutableLiveData("")

    fun login() {
        startLoading()
        scope.launch {
            stopLoading()
        }
    }

    fun forgetPassword() {

    }
}