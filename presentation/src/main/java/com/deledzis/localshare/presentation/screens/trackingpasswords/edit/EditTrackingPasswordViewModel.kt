package com.deledzis.localshare.presentation.screens.trackingpasswords.edit

import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.TrackingPassword
import com.deledzis.localshare.domain.model.entity.Action
import com.deledzis.localshare.domain.model.entity.CloseEditTrackingPasswordAction
import com.deledzis.localshare.domain.model.entity.Entity
import com.deledzis.localshare.domain.model.response.trackingpasswords.DeleteTrackingPasswordResponse
import com.deledzis.localshare.domain.model.response.trackingpasswords.UpdateTrackingPasswordResponse
import com.deledzis.localshare.domain.usecase.trackingpasswords.DeleteTrackingPasswordUseCase
import com.deledzis.localshare.domain.usecase.trackingpasswords.UpdateTrackingPasswordUseCase
import com.deledzis.localshare.infrastructure.extensions.mergeChannels
import com.deledzis.localshare.presentation.base.BaseViewModel
import kotlinx.coroutines.channels.ReceiveChannel
import timber.log.Timber
import javax.inject.Inject

class EditTrackingPasswordViewModel @Inject constructor(
    private val updateTrackingPasswordUseCase: UpdateTrackingPasswordUseCase,
    private val deleteTrackingPasswordUseCase: DeleteTrackingPasswordUseCase
) : BaseViewModel() {

    lateinit var password: TrackingPassword
        private set
    var position: Int = 0
        private set

    override val receiveChannel: ReceiveChannel<Response<Entity, Error>>
        get() = mergeChannels(
            updateTrackingPasswordUseCase.receiveChannel,
            deleteTrackingPasswordUseCase.receiveChannel
        )

    private var _error = MutableLiveData<String>()
    val error = _error

    val description = MutableLiveData<String>()

    private var _action = MutableLiveData<Action?>()
    val action = _action

    fun init(password: TrackingPassword, position: Int) {
        this.password = password
        this.position = position
        description.postValue(password.description)
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
            is UpdateTrackingPasswordResponse -> {
                if (data.result) {
                    _action.postValue(CloseEditTrackingPasswordAction())
                }
            }
            is DeleteTrackingPasswordResponse -> {
                if (data.result) {
                    _action.postValue(CloseEditTrackingPasswordAction())
                }
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

    fun updateTrackingPassword() {
        description.value?.let { password.description = it }
        updateTrackingPasswordUseCase(password)
    }

    fun deleteTrackingPassword() {
        deleteTrackingPasswordUseCase(params = password.password)
    }
}