package com.deledzis.localshare.data.model.auth

data class RegisterUserRequest(
    val email: String,
    val password: String
)