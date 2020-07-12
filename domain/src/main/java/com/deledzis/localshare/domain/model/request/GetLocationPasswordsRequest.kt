package com.deledzis.localshare.domain.model.request

data class GetLocationPasswordsRequest(
    val userId: Int,
    val refresh: Boolean
)