package com.deledzis.localshare.domain.model.request

data class RegisterUserRequest(
    val email: String,
    val password: String
)