package com.deledzis.localshare.remote.model

data class AuthUserRequest(
    val email: String,
    val password: String
)