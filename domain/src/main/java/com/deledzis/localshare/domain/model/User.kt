package com.deledzis.localshare.domain.model

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    var token: String?
)