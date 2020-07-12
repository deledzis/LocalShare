package com.deledzis.localshare.data.model

import com.google.gson.annotations.SerializedName

data class AuthEntity(
    @SerializedName("token")
    val token: String?,
    @SerializedName("user_id")
    val userId: Int
)