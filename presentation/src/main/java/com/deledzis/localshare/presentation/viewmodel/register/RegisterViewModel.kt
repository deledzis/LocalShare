package com.deledzis.localshare.presentation.viewmodel.register

import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.domain.repository.IRegisterRepository
import com.deledzis.localshare.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val repository: IRegisterRepository
) : BaseViewModel() {

    private var _error = MutableLiveData<String>()
    val error = _error

    var email = MutableLiveData("")
    var password = MutableLiveData("")

    fun register() {
        startLoading()
        scope.launch {
            stopLoading()
        }
    }
}