package com.deledzis.localshare.data.source.server.model

import com.google.gson.annotations.SerializedName

data class AuthEntity(
    @SerializedName("token")
    val token: String?,
    @SerializedName("user_id")
    val userId: Int
)