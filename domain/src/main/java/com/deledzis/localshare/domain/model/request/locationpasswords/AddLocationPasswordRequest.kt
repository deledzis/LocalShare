package com.deledzis.localshare.domain.model.request.locationpasswords

data class AddLocationPasswordRequest(
    val password: String,
    val description: String
)