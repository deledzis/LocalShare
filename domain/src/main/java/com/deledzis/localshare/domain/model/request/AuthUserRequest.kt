package com.deledzis.localshare.domain.model.request

data class AuthUserRequest(
    val email: String,
    val password: String
)