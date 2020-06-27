package com.deledzis.localshare.data.model.user

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login")
    val login: String,
    @SerializedName("name")
    val name: String
)