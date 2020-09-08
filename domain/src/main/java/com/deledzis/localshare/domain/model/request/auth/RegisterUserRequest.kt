package com.deledzis.localshare.domain.model.request.auth

data class RegisterUserRequest(
    val email: String,
    val password: String
)