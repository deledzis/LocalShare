package com.deledzis.localshare.presentation.screens.locationpasswords

import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.BaseUserData
import com.deledzis.localshare.domain.model.LocationPassword
import com.deledzis.localshare.domain.usecase.locationpassword.GetLocationPasswordsUseCase
import com.deledzis.localshare.presentation.base.BaseViewModel
import kotlinx.coroutines.channels.ReceiveChannel
import javax.inject.Inject

class LocationPasswordsViewModel @Inject constructor(
    private val getLocationPasswordsUseCase: GetLocationPasswordsUseCase,
    private val baseUserData: BaseUserData
) : BaseViewModel() {
    override val receiveChannel: ReceiveChannel<Response<*, Error>>
        get() = getLocationPasswordsUseCase.receiveChannel

    private var _error = MutableLiveData<String>()
    val error = _error

    private var _locationPasswords = MutableLiveData<List<LocationPassword>>()
    val locationPasswords = _locationPasswords

    init {
        val user = baseUserData.getUser()
        user?.let {
            getLocationPasswordsUseCase(params = it.id)
        }
    }

    override suspend fun resolve(value: Response<*, Error>) {
        value.handleResult(
            stateBlock = ::handleState,
            failureBlock = ::handleFailure,
            successBlock = ::handleSuccess
        )
    }

    private suspend fun handleSuccess(data: Any?) {
        data ?: _locationPasswords.postValue(emptyList())
        if (data is List<*>) {
           _locationPasswords.postValue(data as List<LocationPassword>?)
        }
    }

    private suspend fun handleFailure(error: Error) {
        _error.postValue(error.exception?.message)
        handleLocationPasswordsEmptyResponse()
    }

    private fun handleLocationPasswordsEmptyResponse() {
        val mockList = listOf<LocationPassword>(
            LocationPassword(
                id = 0,
                password = "adssadASKDjaksldjklasd",
                description = "Для друзей",
                active = true,
                ownerId = 0
            ),
            LocationPassword(
                id = 1,
                password = "adssadASKDjaksldjklasdadssadASKDjaksldjklasd",
                description = "Для всех",
                active = false,
                ownerId = 0
            ),
            LocationPassword(
                id = 2,
                password = "adssadASKDjaksldjklasd",
                description = "Для семьи",
                active = true,
                ownerId = 0
            )
        )

        _locationPasswords.postValue(mockList)
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

    fun handleActiveTrigger(password: LocationPassword) {
        // TODO
//        password.active = !password.active
    }
}