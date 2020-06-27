package com.deledzis.localshare.api

import com.deledzis.localshare.data.model.auth.*
import com.deledzis.localshare.data.model.fcm_token.RegisterToPushesRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    /**
     * Авторизация
     **/
    // Авторизация
    @POST("auth")
    suspend fun authUser(
        @Body request: AuthUserRequest
    ): Response<Auth>

    // Авторизация
    @POST("register")
    suspend fun registerUser(
        @Body request: RegisterUserRequest
    ): Response<Auth>

    // Проверить токен
    @POST("auth/verify")
    suspend fun verifyToken(
        @Body request: VerifyTokenRequest
    ): Response<Auth>

    // Обновить токен
    @POST("auth/refresh")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): Response<Auth>

    /**
     * Push уведомления
     **/
    @POST("device/gcm")
    suspend fun registerToPushes(
        @Body request: RegisterToPushesRequest
    ): Response<Any>
}