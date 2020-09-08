package com.deledzis.localshare.presentation.screens.locationpasswords.edit

import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.LocationPassword
import com.deledzis.localshare.domain.model.entity.Action
import com.deledzis.localshare.domain.model.entity.CloseEditLocationPasswordAction
import com.deledzis.localshare.domain.model.entity.Entity
import com.deledzis.localshare.domain.model.response.locationpasswords.DeleteLocationPasswordResponse
import com.deledzis.localshare.domain.model.response.locationpasswords.UpdateLocationPasswordResponse
import com.deledzis.localshare.domain.usecase.locationpasswords.DeleteLocationPasswordUseCase
import com.deledzis.localshare.domain.usecase.locationpasswords.UpdateLocationPasswordUseCase
import com.deledzis.localshare.infrastructure.extensions.mergeChannels
import com.deledzis.localshare.presentation.base.BaseViewModel
import kotlinx.coroutines.channels.ReceiveChannel
import timber.log.Timber
import javax.inject.Inject

class EditLocationPasswordViewModel @Inject constructor(
    private val updateLocationPasswordUseCase: UpdateLocationPasswordUseCase,
    private val deleteLocationPasswordUseCase: DeleteLocationPasswordUseCase
) : BaseViewModel() {

    lateinit var password: LocationPassword
        private set
    var position: Int = 0
        private set

    override val receiveChannel: ReceiveChannel<Response<Entity, Error>>
        get() = mergeChannels(
            updateLocationPasswordUseCase.receiveChannel,
            deleteLocationPasswordUseCase.receiveChannel
        )

    private var _error = MutableLiveData<String>()
    val error = _error

    val description = MutableLiveData<String>()
    val active = MutableLiveData<Boolean>()

    private var _action = MutableLiveData<Action?>()
    val action = _action

    fun init(password: LocationPassword, position: Int) {
        this.password = password
        this.position = position
        description.postValue(password.description)
        active.postValue(password.active)
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
            is UpdateLocationPasswordResponse -> {
                if (data.result) {
                    _action.postValue(CloseEditLocationPasswordAction())
                }
            }
            is DeleteLocationPasswordResponse -> {
                if (data.result) {
                    _action.postValue(CloseEditLocationPasswordAction())
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

    fun updateLocationPassword() {
        active.value?.let { password.active = it }
        description.value?.let { password.description = it }
        updateLocationPasswordUseCase(password)
    }

    fun handleActiveTrigger() {
        active.value?.let { active.postValue(!it) }
    }

    fun deleteLocationPassword() {
        deleteLocationPasswordUseCase(params = password.password)
    }
}