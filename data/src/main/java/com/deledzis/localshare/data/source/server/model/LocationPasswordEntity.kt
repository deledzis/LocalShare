package com.deledzis.localshare.data.source.server.model

import com.google.gson.annotations.SerializedName

data class LocationPasswordEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("password")
    val password: String,
    @SerializedName("description")
    val description: String
)