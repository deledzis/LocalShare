package com.deledzis.localshare.domain.model.request

data class AddLocationPasswordRequest(
    val password: String,
    val description: String
)