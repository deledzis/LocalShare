package com.deledzis.localshare.presentation.screens.locationpasswords

import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.BaseUserData
import com.deledzis.localshare.domain.model.LocationPassword
import com.deledzis.localshare.domain.model.entity.*
import com.deledzis.localshare.domain.model.request.locationpasswords.GetLocationPasswordsRequest
import com.deledzis.localshare.domain.model.response.locationpasswords.GetLocationPasswordsResponse
import com.deledzis.localshare.domain.usecase.locationpasswords.GetLocationPasswordsUseCase
import com.deledzis.localshare.domain.usecase.locationpasswords.UpdateLocationPasswordUseCase
import com.deledzis.localshare.infrastructure.extensions.mergeChannels
import com.deledzis.localshare.presentation.base.BaseViewModel
import kotlinx.coroutines.channels.ReceiveChannel
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationPasswordsViewModel @Inject constructor(
    private val getLocationPasswordsUseCase: GetLocationPasswordsUseCase,
    private val updateLocationPasswordUseCase: UpdateLocationPasswordUseCase,
    private val userData: BaseUserData
) : BaseViewModel(), ILocationPasswordActionsHandler {
    override val receiveChannel: ReceiveChannel<Response<Entity, Error>>
        get() = mergeChannels(
            getLocationPasswordsUseCase.receiveChannel,
            updateLocationPasswordUseCase.receiveChannel
        )

    private var _error = MutableLiveData<String>()
    val error = _error

    private var _locationPasswords = MutableLiveData<List<LocationPassword>>(emptyList())
    val locationPasswords = _locationPasswords

    private var _locationPasswordUpdate = MutableLiveData<Pair<LocationPassword, Int>>()
    val locationPasswordUpdate = _locationPasswordUpdate

    val description = MutableLiveData<String>()

    private var _action = MutableLiveData<Action?>()
    val action = _action

    fun fetchData() {
        getLocationPasswords()
    }

    fun refreshLocationPasswords() {
        getLocationPasswords(refresh = true)
    }

    override suspend fun resolve(value: Response<Entity, Error>) {
        value.handleResult(
            stateBlock = ::handleState,
            failureBlock = ::handleFailure,
            successBlock = ::handleSuccess
        )
    }

    override fun handleActiveTrigger(password: LocationPassword, position: Int) {
        password.active = !password.active
        updateLocationPasswordUseCase(password)
        _locationPasswordUpdate.postValue(password to position)
    }

    override fun handleOnClick(password: LocationPassword, position: Int) {
        _action.postValue(ShowEditLocationPasswordAction(password = password, position = position))
    }

    override fun handleOnAddClick() {
        _action.postValue(ShowAddLocationPasswordAction())
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun handleSuccess(data: Any?) {
        data ?: return
        Timber.i("Handle Success: $data")
        when (data) {
            is GetLocationPasswordsResponse -> {
                _locationPasswords.postValue(data.items)
            }
        }
    }

    private suspend fun handleFailure(error: Error) {
        Timber.e("Handle Failure: ${error.exception}")
        _error.postValue(error.exception?.message)
        if (_locationPasswords.value == null) {
            _locationPasswords.postValue(emptyList())
        }
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

    /*fun createLocationPassword() {
        if (description.value.isNullOrBlank()) {
            return
        }

        val password = generateMockLocationPassword()

        addLocationPasswordUseCase(
            params = AddLocationPasswordRequest(
                password = password,
                description = description.value!!
            )
        )

        // TODO: remove when backend work
        val list = _locationPasswords.value?.toMutableList() ?: mutableListOf()
        list.add(
            LocationPassword(
                password = password,
                description = description.value!!,
                active = false,
                ownerId = 1
            )
        )
        _locationPasswords.postValue(list)
        _action.postValue(CloseAddLocationPasswordAction())
        saveLocationPasswordsUseCase(params = list)
    }*/

    private fun getLocationPasswords(refresh: Boolean = false) {
        userData.getUser()?.let {
            getLocationPasswordsUseCase(
                params = GetLocationPasswordsRequest(
                    userId = it.id,
                    refresh = refresh
                )
            )
        }
    }

    fun handleAddDialogDismiss() {
        refreshLocationPasswords()
        _action.postValue(CloseAddLocationPasswordAction())
    }

    fun handleEditDialogDismiss() {
        refreshLocationPasswords()
        _action.postValue(CloseEditLocationPasswordAction())
    }
}