package com.deledzis.localshare.data.source.server.model

import com.google.gson.annotations.SerializedName

data class UserEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String
)