package com.deledzis.localshare.presentation.screens.locationpasswords

import com.deledzis.localshare.domain.model.LocationPassword

interface ILocationPasswordActionsHandler {
    fun handleOnClick(password: LocationPassword)

    fun handleActiveTrigger(password: LocationPassword)
}