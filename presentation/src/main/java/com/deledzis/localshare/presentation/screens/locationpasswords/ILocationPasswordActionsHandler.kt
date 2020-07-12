package com.deledzis.localshare.presentation.screens.locationpasswords

import com.deledzis.localshare.domain.model.LocationPassword

interface ILocationPasswordActionsHandler {
    fun handleOnClick(password: LocationPassword, position: Int)

    fun handleActiveTrigger(password: LocationPassword, position: Int)

    fun handleOnAddClick()
}