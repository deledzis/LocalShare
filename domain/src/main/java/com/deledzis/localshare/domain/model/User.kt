package com.deledzis.localshare.domain.model

data class User(
    val id: Int,
    val email: String,
    var token: String?
)