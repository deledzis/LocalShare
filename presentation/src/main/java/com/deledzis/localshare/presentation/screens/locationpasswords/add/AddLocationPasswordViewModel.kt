package com.deledzis.localshare.presentation.screens.locationpasswords.add

import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.entity.Action
import com.deledzis.localshare.domain.model.entity.CloseAddLocationPasswordAction
import com.deledzis.localshare.domain.model.entity.Entity
import com.deledzis.localshare.domain.model.request.locationpasswords.AddLocationPasswordRequest
import com.deledzis.localshare.domain.model.response.locationpasswords.AddLocationPasswordResponse
import com.deledzis.localshare.domain.usecase.locationpasswords.AddLocationPasswordUseCase
import com.deledzis.localshare.infrastructure.extensions.mergeChannels
import com.deledzis.localshare.presentation.base.BaseViewModel
import kotlinx.coroutines.channels.ReceiveChannel
import timber.log.Timber
import javax.inject.Inject

class AddLocationPasswordViewModel @Inject constructor(
    private val addLocationPasswordUseCase: AddLocationPasswordUseCase
) : BaseViewModel() {

    override val receiveChannel: ReceiveChannel<Response<Entity, Error>>
        get() = mergeChannels(
            addLocationPasswordUseCase.receiveChannel
        )

    private var _error = MutableLiveData<String>()
    val error = _error

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
            is AddLocationPasswordResponse -> {
                if (data.result) {
                    _action.postValue(CloseAddLocationPasswordAction())
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

    fun createLocationPassword() {
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
    }

    private fun generateMockLocationPassword(): String {
        val charPool: List<Char> = ('A'..'Z') + ('0'..'9')

        return "0x" + (1..32)
            .map { (charPool.indices).random() }
            .map(charPool::get)
            .joinToString("")
    }
}