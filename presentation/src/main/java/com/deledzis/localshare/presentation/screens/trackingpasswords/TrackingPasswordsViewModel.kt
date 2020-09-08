package com.deledzis.localshare.presentation.screens.trackingpasswords

import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.BaseUserData
import com.deledzis.localshare.domain.model.TrackingPassword
import com.deledzis.localshare.domain.model.entity.*
import com.deledzis.localshare.domain.model.request.trackingpasswords.GetTrackingPasswordsRequest
import com.deledzis.localshare.domain.model.response.trackingpasswords.GetTrackingPasswordsResponse
import com.deledzis.localshare.domain.usecase.trackingpasswords.GetTrackingPasswordsUseCase
import com.deledzis.localshare.infrastructure.extensions.mergeChannels
import com.deledzis.localshare.presentation.base.BaseViewModel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class TrackingPasswordsViewModel @Inject constructor(
    private val getTrackingPasswordsUseCase: GetTrackingPasswordsUseCase,
    private val userData: BaseUserData
) : BaseViewModel(), ITrackingPasswordActionsHandler {
    override val receiveChannel: ReceiveChannel<Response<Entity, Error>>
        get() = mergeChannels(
            getTrackingPasswordsUseCase.receiveChannel
        )

    private var _error = MutableLiveData<String>()
    val error = _error

    private var _trackingPasswords = MutableLiveData<List<TrackingPassword>>()
    val trackingPasswords = _trackingPasswords

    private var _trackingPasswordUpdate = MutableLiveData<Pair<TrackingPassword, Int>>()
    val trackingPasswordUpdate = _trackingPasswordUpdate

    private var _trackingPasswordSelection = MutableLiveData<TrackingPassword>()
    val trackingPasswordSelection = _trackingPasswordSelection

    private var _action = MutableLiveData<Action?>()
    val action = _action

    fun fetchData() {
        getTrackingPasswords()
    }

    fun refreshTrackingPasswords() {
        getTrackingPasswords(refresh = true)
    }

    override suspend fun resolve(value: Response<Entity, Error>) {
        value.handleResult(
            stateBlock = ::handleState,
            failureBlock = ::handleFailure,
            successBlock = ::handleSuccess
        )
    }

    override fun handleOnClick(password: TrackingPassword, position: Int) {
        _action.postValue(ShowEditTrackingPasswordAction(password = password, position = position))
    }

    override fun handleOnAddClick() {
        _action.postValue(ShowAddTrackingPasswordAction())
    }

    override fun handleOnMapClick(password: TrackingPassword, position: Int) {
        launch {
            _trackingPasswordSelection.postValue(password)
            delay(200)
            _trackingPasswordSelection.postValue(null)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun handleSuccess(data: Any?) {
        data ?: return
        Timber.i("Handle Success: $data")
        when (data) {
            is GetTrackingPasswordsResponse -> {
                _trackingPasswords.postValue(data.items)
//                    saveTrackingPasswordsUseCase(data.items)
                /*if (data.items.isNotEmpty()) {

                } else {
                    // TODO: remove later
                    val test = listOf(
                        TrackingPassword(
                            password = "IO43HF0IJQF-O32JF-2F2JF3-",
                            description = "Миша",
                            active = true,
                            lastCoordinates = LastCoordinates(
                                lat = 57.9698694,
                                lng = 31.3442704
                            ),
                            lastUpdateTime = Calendar.getInstance().timeInMillis
                        ),
                        TrackingPassword(
                            password = "UEI1DUD9012D021J",
                            description = "Степан Петрович",
                            active = false,
                            lastCoordinates = LastCoordinates(
                                lat = 56.9698694,
                                lng = 30.3442704
                            ),
                            lastUpdateTime = Calendar.getInstance().timeInMillis - 49590
                        ),
                        TrackingPassword(
                            password = "12ID01JD02J302F",
                            description = "Вася",
                            active = false,
                            lastCoordinates = LastCoordinates(
                                lat = 60.9698694,
                                lng = 30.3442704
                            ),
                            lastUpdateTime = Calendar.getInstance().timeInMillis - 500534
                        )
                    )
                    _trackingPasswords.postValue(test)
                    saveTrackingPasswordsUseCase(test)
                }*/
            }
        }
    }

    private suspend fun handleFailure(error: Error) {
        Timber.e("Handle Failure: ${error.exception}")
        _error.postValue(error.exception?.message)
        if (_trackingPasswords.value == null) {
            _trackingPasswords.postValue(emptyList())
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

    fun handleAddDialogDismiss() {
        refreshTrackingPasswords()
        _action.postValue(CloseAddTrackingPasswordAction())
    }

    fun handleEditDialogDismiss() {
        refreshTrackingPasswords()
        _action.postValue(CloseEditTrackingPasswordAction())
    }

    private fun getTrackingPasswords(refresh: Boolean = false) {
        userData.getUser()?.let {
            getTrackingPasswordsUseCase(
                params = GetTrackingPasswordsRequest(
                    userId = it.id,
                    refresh = refresh
                )
            )
        }
    }
}