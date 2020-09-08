package com.deledzis.localshare.data.model

import com.google.gson.annotations.SerializedName

data class TrackingPasswordEntity(
    @SerializedName("id")
    val id: Long,

    @SerializedName("password")
    val password: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("active")
    val active: Boolean,

    @SerializedName("last_coordinates")
    val lastCoordinates: LastCoordinatesEntity,

    @SerializedName("last_update_time")
    val lastUpdateTime: Long
)

class LastCoordinatesEntity(
    val lat: Double,
    val lng: Double
)