package com.deledzis.localshare.remote.model

data class AddTrackingPasswordRequest(
    val password: String,
    val description: String
)