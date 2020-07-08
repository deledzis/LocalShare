package com.deledzis.localshare.domain.model

data class LocationPassword(
    val id: Int,
    val password: String,
    val description: String,
    val active: Boolean,
    val ownerId: Int
)