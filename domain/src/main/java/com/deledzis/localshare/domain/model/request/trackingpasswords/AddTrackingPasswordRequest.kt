package com.deledzis.localshare.domain.model.request.trackingpasswords

data class AddTrackingPasswordRequest(
    val password: String,
    val description: String
)