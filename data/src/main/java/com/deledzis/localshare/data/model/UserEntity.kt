package com.deledzis.localshare.data.model

import com.google.gson.annotations.SerializedName

data class UserEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("token")
    val token: String
)