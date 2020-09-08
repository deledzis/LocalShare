package com.deledzis.localshare.domain.model

import java.io.Serializable

data class Auth(
    val token: String?,
    val userId: Int
): Serializable