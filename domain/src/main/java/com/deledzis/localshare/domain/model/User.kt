package com.deledzis.localshare.domain.model

import java.io.Serializable

data class User(
    val id: Int,
    val email: String,
    var token: String?
) : Serializable