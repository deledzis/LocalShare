package com.deledzis.localshare.domain.model

import java.io.Serializable

data class TrackingPassword(
    val id: Long,
    var password: String,
    var description: String,
    var active: Boolean,
    val lastCoordinates: LastCoordinates,
    val lastUpdateTime: Long
) : Serializable

class LastCoordinates(
    var lat: Double,
    var lng: Double
)