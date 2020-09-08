package com.deledzis.localshare.domain.model.request.locationpasswords

data class GetLocationPasswordsRequest(
    val userId: Int,
    val refresh: Boolean
)