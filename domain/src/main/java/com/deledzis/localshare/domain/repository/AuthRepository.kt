package com.deledzis.localshare.domain.repository

import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.entity.auth.*

interface AuthRepository {

    suspend fun auth(email: String, password: String): Response<AuthResponse, Error>

    suspend fun register(email: String, password: String): Response<RegisterResponse, Error>

    suspend fun verifyToken(token: String): Response<VerifyTokenResponse, Error>

    suspend fun refreshToken(token: String): Response<RefreshTokenResponse, Error>

    suspend fun forgetPassword(email: String): Response<ForgetPasswordResponse, Error>

    suspend fun getUser(id: Int): Response<GetUserResponse, Error>
}