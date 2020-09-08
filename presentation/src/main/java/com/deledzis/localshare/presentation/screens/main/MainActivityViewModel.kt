package com.deledzis.localshare.presentation.screens.main

import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.entity.Entity
import com.deledzis.localshare.presentation.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainActivityViewModel @Inject constructor() : BaseViewModel() {

    override val receiveChannel: ReceiveChannel<Response<Entity, Error>>
        get() = Channel()

    var inTopLevelFragment = MutableLiveData<Pair<Boolean, Toolbar?>>(true to null)

    override suspend fun resolve(value: Response<Entity, Error>) {}

    fun setInTopLevelFragment() {
        inTopLevelFragment.postValue(true to null)
    }

    fun setInSecondaryLevelFragment(toolbar: Toolbar) {
        inTopLevelFragment.postValue(false to toolbar)
    }
}