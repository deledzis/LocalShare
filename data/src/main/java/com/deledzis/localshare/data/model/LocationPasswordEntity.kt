package com.deledzis.localshare.data.model

import com.google.gson.annotations.SerializedName

data class LocationPasswordEntity(
    @SerializedName("password")
    val password: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("owner_id")
    val ownerId: Int
)