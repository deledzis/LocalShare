package com.deledzis.localshare.remote

import com.deledzis.localshare.common.Constants
import com.deledzis.localshare.data.source.server.model.AuthEntity
import com.deledzis.localshare.data.source.server.model.LocationPasswordEntity
import com.deledzis.localshare.data.source.server.model.UserEntity
import com.deledzis.localshare.remote.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    /**
     * Авторизация
     **/
    // Авторизация
    @POST("auth")
    suspend fun auth(
        @Body request: AuthUserRequest
    ): Response<AuthEntity>

    // Проверить токен
    @POST("auth/verify")
    suspend fun verifyToken(
        @Body request: VerifyTokenRequest
    ): Response<AuthEntity>

    // Обновить токен
    @POST("auth/refresh")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): Response<AuthEntity>


    /**
     * Регистрация
     **/
    @POST("remember_password")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<AuthEntity>


    /**
     * Восстановление пароля
     **/
    @POST("remember_password")
    suspend fun recoverPassword(
        @Body request: RecoverRequest
    ): Response<Boolean>


    /**
     * Пользователь
     **/
    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") id: Int
    ): Response<UserEntity>


    /**
     * Пользовательские пароли
     **/
    @GET("passwords")
    suspend fun getLocationPasswords(
        @Query("take") take: Int = Constants.API_DEFAULT_TAKE,
        @Query("page") page: Int = 0,
        @Query("user") userId: Int
    ): Response<List<LocationPasswordEntity>>

    @POST("passwords")
    suspend fun addLocationPassword(
        @Body request: AddLocationPasswordRequest
    ): Response<Boolean>

    @PUT("passwords")
    suspend fun updateLocationPassword(
        @Body request: UpdateLocationPasswordRequest
    ): Response<Boolean>

    @POST("passwords")
    suspend fun deleteLocationPassword(
        @Body request: DeleteLocationPasswordRequest
    ): Response<Boolean>
}