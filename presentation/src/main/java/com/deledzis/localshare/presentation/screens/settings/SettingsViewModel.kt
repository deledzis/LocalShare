package com.deledzis.localshare.presentation.screens.settings

import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.entity.Entity
import com.deledzis.localshare.presentation.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import javax.inject.Inject

class SettingsViewModel @Inject constructor() : BaseViewModel() {
    override val receiveChannel: ReceiveChannel<Response<Entity, Error>>
        get() = Channel()

    override suspend fun resolve(value: Response<Entity, Error>) {}
}