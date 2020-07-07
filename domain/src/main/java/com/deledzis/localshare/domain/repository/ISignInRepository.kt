package com.deledzis.localshare.domain.repository

import com.deledzis.localshare.domain.model.Auth
import com.deledzis.localshare.domain.model.User

interface ISignInRepository {
    suspend fun auth(email: String, password: String): Auth?

    suspend fun verifyToken(token: String): Auth?

    suspend fun refreshToken(token: String): Auth?

    suspend fun getUser(id: Int): User?
}