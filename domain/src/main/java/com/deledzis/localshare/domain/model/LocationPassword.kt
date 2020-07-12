package com.deledzis.localshare.domain.model

data class LocationPassword(
    var password: String,
    var description: String,
    var active: Boolean,
    val ownerId: Int
)