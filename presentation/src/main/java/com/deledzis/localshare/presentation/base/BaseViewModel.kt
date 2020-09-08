package com.deledzis.localshare.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.entity.Entity
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    private val job = Job()
    protected abstract val receiveChannel: ReceiveChannel<Response<Entity, Error>>

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val loading = MutableLiveData(false)
    val loadingError = MutableLiveData(false)

    abstract suspend fun resolve(value: Response<Entity, Error>)

    init {
        processStream()
    }

    private fun processStream() {
        launch {
            receiveChannel.consumeEach {
                resolve(it)
            }
        }
    }

    protected open fun startLoading() {
        loading.postValue(true)
        loadingError.postValue(false)
    }

    protected open fun stopLoading(error: Boolean = false) {
        loading.postValue(false)
        loadingError.postValue(error)
    }

    override fun onCleared() {
        Timber.d("onCleared: $this")
        receiveChannel.cancel()
        coroutineContext.cancel()
        stopLoading()
        super.onCleared()
    }
}