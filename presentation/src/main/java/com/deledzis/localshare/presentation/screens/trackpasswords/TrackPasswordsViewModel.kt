package com.deledzis.localshare.presentation.screens.trackpasswords

import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.BaseUserData
import com.deledzis.localshare.domain.model.LocationPassword
import com.deledzis.localshare.domain.model.entity.Entity
import com.deledzis.localshare.domain.model.entity.locationpassword.*
import com.deledzis.localshare.domain.model.request.AddLocationPasswordRequest
import com.deledzis.localshare.domain.model.request.GetLocationPasswordsRequest
import com.deledzis.localshare.domain.usecase.locationpassword.AddLocationPasswordUseCase
import com.deledzis.localshare.domain.usecase.locationpassword.GetLocationPasswordsUseCase
import com.deledzis.localshare.domain.usecase.locationpassword.SaveLocationPasswordsUseCase
import com.deledzis.localshare.domain.usecase.locationpassword.UpdateLocationPasswordUseCase
import com.deledzis.localshare.infrastructure.extensions.mergeChannels
import com.deledzis.localshare.presentation.base.BaseViewModel
import kotlinx.coroutines.channels.ReceiveChannel
import timber.log.Timber
import javax.inject.Inject

class TrackPasswordsViewModel @Inject constructor(
    private val getLocationPasswordsUseCase: GetLocationPasswordsUseCase,
    private val updateLocationPasswordUseCase: UpdateLocationPasswordUseCase,
    private val saveLocationPasswordsUseCase: SaveLocationPasswordsUseCase,
    private val addLocationPasswordUseCase: AddLocationPasswordUseCase,
    private val userData: BaseUserData
) : BaseViewModel(), ITrackPasswordActionsHandler {
    override val receiveChannel: ReceiveChannel<Response<Entity, Error>>
        get() = mergeChannels(
            getLocationPasswordsUseCase.receiveChannel,
            updateLocationPasswordUseCase.receiveChannel,
            saveLocationPasswordsUseCase.receiveChannel,
            addLocationPasswordUseCase.receiveChannel
        )

    private var _error = MutableLiveData<String>()
    val error = _error

    private var _userError = MutableLiveData<Boolean>()
    val userError = _userError

    private var _locationPasswords = MutableLiveData<List<LocationPassword>>()
    val locationPasswords = _locationPasswords

    private var _locationPasswordUpdate = MutableLiveData<Pair<LocationPassword, Int>>()
    val locationPasswordUpdate = _locationPasswordUpdate


    fun fetchData() {
        userData.getUser()?.let {
            getLocationPasswordsUseCase(
                params = GetLocationPasswordsRequest(
                    userId = it.id,
                    refresh = false
                )
            )
        } ?: _userError.postValue(true)
    }

    override suspend fun resolve(value: Response<Entity, Error>) {
        value.handleResult(
            stateBlock = ::handleState,
            failureBlock = ::handleFailure,
            successBlock = ::handleSuccess
        )
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun handleSuccess(data: Any?) {
        data ?: return
//        Timber.e("Handle Success: $data")
        when (data) {
            is GetLocationPasswordsResponse -> {
                _locationPasswords.postValue(data.items)
            }
            is UpdateLocationPasswordResponse -> {
                if (data.result) {
                    userData.getUser()?.let {
                        getLocationPasswordsUseCase(
                            params = GetLocationPasswordsRequest(
                                userId = it.id,
                                refresh = true
                            )
                        )
                    } ?: _userError.postValue(true)
                }
            }
            is AddLocationPasswordResponse -> {
                if (data.result) {
                    userData.getUser()?.let {
                        getLocationPasswordsUseCase(
                            params = GetLocationPasswordsRequest(
                                userId = it.id,
                                refresh = true
                            )
                        )
                    } ?: _userError.postValue(true)
                }
            }
            is DeleteLocationPasswordResponse -> {
                if (data.result) {
                    userData.getUser()?.let {
                        getLocationPasswordsUseCase(
                            params = GetLocationPasswordsRequest(
                                userId = it.id,
                                refresh = true
                            )
                        )
                    } ?: _userError.postValue(true)
                }
            }
            is SaveLocationPasswordsResponse -> {

            }
            is ClearLocationPasswordsResponse -> {

            }
        }
    }

    private suspend fun handleFailure(error: Error) {
//        Timber.e("Handle Failure: ${error.exception}")
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

    fun refreshLocationPasswords() {
        userData.getUser()?.let {
            getLocationPasswordsUseCase(
                params = GetLocationPasswordsRequest(
                    userId = it.id,
                    refresh = true
                )
            )
        } ?: _userError.postValue(true)
    }

    override fun handleOnClick(password: LocationPassword, position: Int) {

    }

    override fun handleOnAddClick() {
        val mockLocationPassword = generateMockLocationPassword()
        addLocationPasswordUseCase(
            params = AddLocationPasswordRequest(
                password = mockLocationPassword.password,
                description = mockLocationPassword.description
            )
        )

        // TODO: This whole part to be removed when backend works
        val list = _locationPasswords.value?.toMutableList() ?: mutableListOf()
        list.add(mockLocationPassword)
        _locationPasswords.postValue(list)
        Timber.e("Saving $list")
//        saveLocationPasswordsUseCase(params = list)
    }

    private fun generateMockLocationPassword(): LocationPassword {
        val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val password = (1..32)
            .map { (charPool.indices).random() }
            .map(charPool::get)
            .joinToString("")

        return LocationPassword(
            password = password,
            description = "Для друзей",
            active = false,
            ownerId = 1
        )
    }
}