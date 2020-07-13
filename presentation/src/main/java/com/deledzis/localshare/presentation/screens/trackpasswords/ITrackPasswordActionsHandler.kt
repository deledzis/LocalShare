package com.deledzis.localshare.presentation.screens.trackpasswords

import com.deledzis.localshare.domain.model.LocationPassword

interface ITrackPasswordActionsHandler {
    fun handleOnClick(password: LocationPassword, position: Int)

    fun handleOnAddClick()
}