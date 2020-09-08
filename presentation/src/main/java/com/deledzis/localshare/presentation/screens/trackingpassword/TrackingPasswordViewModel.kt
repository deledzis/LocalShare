package com.deledzis.localshare.presentation.screens.trackingpassword

import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.BaseUserData
import com.deledzis.localshare.domain.model.TrackingPassword
import com.deledzis.localshare.domain.model.entity.Entity
import com.deledzis.localshare.domain.model.request.trackingpasswords.GetTrackingPasswordsRequest
import com.deledzis.localshare.domain.model.response.trackingpasswords.GetTrackingPasswordsResponse
import com.deledzis.localshare.domain.usecase.trackingpasswords.GetTrackingPasswordsUseCase
import com.deledzis.localshare.domain.usecase.trackingpasswords.UpdateTrackingPasswordUseCase
import com.deledzis.localshare.infrastructure.extensions.mergeChannels
import com.deledzis.localshare.presentation.base.BaseViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class TrackingPasswordViewModel @Inject constructor(
    private val getTrackingPasswordsUseCase: GetTrackingPasswordsUseCase,
    private val updateTrackingPasswordUseCase: UpdateTrackingPasswordUseCase,
    private val userData: BaseUserData
) : BaseViewModel() {
    override val receiveChannel: ReceiveChannel<Response<Entity, Error>>
        get() = mergeChannels(
            getTrackingPasswordsUseCase.receiveChannel,
            updateTrackingPasswordUseCase.receiveChannel
        )

    private lateinit var password: TrackingPassword

    private var _error = MutableLiveData<String>()
    val error = _error

    private var _latLng = MutableLiveData<LatLng>()
    val latLng = _latLng

    fun init(password: TrackingPassword) {
        this.password = password
        launch {
            while(coroutineContext.isActive) {
                //do your request
                delay(5000)
                userData.getUser()?.let {
                    getTrackingPasswordsUseCase(
                        params = GetTrackingPasswordsRequest(
                            userId = it.id,
                            refresh = false
                        )
                    )
                }
            }
        }
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
        Timber.i("Handle Success: $data")
        when (data) {
            is GetTrackingPasswordsResponse -> {
                password = data.items.find {
                    it.password == this.password.password
                } ?: return

                password.lastCoordinates.lat += 0.000090
                password.lastCoordinates.lng += 0.000050
                val newLatLng = LatLng(
                    password.lastCoordinates.lat,
                    password.lastCoordinates.lng
                )
                _latLng.postValue(newLatLng)
                updateTrackingPasswordUseCase(password)
            }
        }
    }

    private suspend fun handleFailure(error: Error) {
        Timber.e("Handle Failure: ${error.exception}")
        _error.postValue(error.exception?.message)
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
}