package com.deledzis.localshare.domain.model.request.auth

data class AuthUserRequest(
    val email: String,
    val password: String
)