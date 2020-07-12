package com.deledzis.localshare.presentation.screens.register

import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.User
import com.deledzis.localshare.domain.usecase.locationpassword.GetLocationPasswordsUseCase
import com.deledzis.localshare.presentation.base.BaseViewModel
import kotlinx.coroutines.channels.ReceiveChannel
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
//    private val repository: RegisterRepository
    private val getLocationPasswordsUseCase: GetLocationPasswordsUseCase
) : BaseViewModel() {

    override val receiveChannel: ReceiveChannel<Response<*, Error>>
        get() = getLocationPasswordsUseCase.receiveChannel

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

    fun register() {
        _error.value = null
        _emailError.value = null
        _passwordError.value = null

        startLoading()
        /*scope.launch {
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
        }*/
    }
}