package com.deledzis.localshare.remote.model

data class UpdateLocationPasswordRequest(
    val id: Int,
    val password: String,
    val description: String
)