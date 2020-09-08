package com.deledzis.localshare.domain.model.request.trackingpasswords

data class GetTrackingPasswordsRequest(
    val userId: Int,
    val refresh: Boolean
)