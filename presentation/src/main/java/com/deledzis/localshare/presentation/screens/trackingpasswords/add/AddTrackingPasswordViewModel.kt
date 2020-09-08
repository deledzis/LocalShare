package com.deledzis.localshare.presentation.screens.trackingpasswords.add

import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.entity.Action
import com.deledzis.localshare.domain.model.entity.CloseAddTrackingPasswordAction
import com.deledzis.localshare.domain.model.entity.Entity
import com.deledzis.localshare.domain.model.request.trackingpasswords.AddTrackingPasswordRequest
import com.deledzis.localshare.domain.model.response.trackingpasswords.AddTrackingPasswordResponse
import com.deledzis.localshare.domain.usecase.trackingpasswords.AddTrackingPasswordUseCase
import com.deledzis.localshare.infrastructure.extensions.mergeChannels
import com.deledzis.localshare.presentation.base.BaseViewModel
import kotlinx.coroutines.channels.ReceiveChannel
import timber.log.Timber
import javax.inject.Inject

class AddTrackingPasswordViewModel @Inject constructor(
    private val addTrackingPasswordUseCase: AddTrackingPasswordUseCase
) : BaseViewModel() {

    override val receiveChannel: ReceiveChannel<Response<Entity, Error>>
        get() = mergeChannels(
            addTrackingPasswordUseCase.receiveChannel
        )

    private var _error = MutableLiveData<String>()
    val error = _error

    val password = MutableLiveData<String>()
    val description = MutableLiveData<String>()

    private var _action = MutableLiveData<Action?>()
    val action = _action

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
            is AddTrackingPasswordResponse -> {
                if (data.result) {
                    _action.postValue(CloseAddTrackingPasswordAction())
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

    fun createTrackingPassword() {
        if (password.value.isNullOrBlank()) {
            return
        }

        if (description.value.isNullOrBlank()) {
            return
        }

        addTrackingPasswordUseCase(
            params = AddTrackingPasswordRequest(
                password = password.value!!,
                description = description.value!!
            )
        )
    }
}