package com.deledzis.localshare.domain.model

import java.io.Serializable

data class LocationPassword(
    val id: Long,
    var password: String,
    var description: String,
    var active: Boolean,
    val ownerId: Int
) : Serializable