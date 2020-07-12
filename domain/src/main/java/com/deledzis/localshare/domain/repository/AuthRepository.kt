package com.deledzis.localshare.domain.repository

import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.Auth
import com.deledzis.localshare.domain.model.User

interface AuthRepository {

    suspend fun auth(email: String, password: String): Response<Auth, Error>

    suspend fun register(email: String, password: String): Response<Auth, Error>

    suspend fun verifyToken(token: String): Response<Auth, Error>

    suspend fun refreshToken(token: String): Response<Auth, Error>

    suspend fun forgetPassword(email: String): Response<Boolean, Error>

    suspend fun getUser(id: Int): Response<User, Error>
}