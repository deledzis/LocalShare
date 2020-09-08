package com.deledzis.localshare.presentation.screens.trackingpasswords

import com.deledzis.localshare.domain.model.TrackingPassword

interface ITrackingPasswordActionsHandler {
    fun handleOnClick(password: TrackingPassword, position: Int)

    fun handleOnMapClick(password: TrackingPassword, position: Int)

    fun handleOnAddClick()
}