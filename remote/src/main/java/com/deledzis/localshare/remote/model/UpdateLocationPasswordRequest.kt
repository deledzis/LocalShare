package com.deledzis.localshare.remote.model

data class UpdateLocationPasswordRequest(
    val password: String,
    val description: String
)