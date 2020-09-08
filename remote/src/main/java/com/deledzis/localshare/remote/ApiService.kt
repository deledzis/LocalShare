package com.deledzis.localshare.remote

import com.deledzis.localshare.common.Constants
import com.deledzis.localshare.data.model.AuthEntity
import com.deledzis.localshare.data.model.LocationPasswordEntity
import com.deledzis.localshare.data.model.TrackingPasswordEntity
import com.deledzis.localshare.data.model.UserEntity
import com.deledzis.localshare.remote.model.*
import retrofit2.http.*

interface ApiService {
    /**
     * Авторизация
     **/
    // Авторизация
    @POST("auth")
    suspend fun auth(
        @Body request: AuthUserRequest
    ): AuthEntity

    // Проверить токен
    @POST("auth/verify")
    suspend fun verifyToken(
        @Body request: VerifyTokenRequest
    ): AuthEntity

    // Обновить токен
    @POST("auth/refresh")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): AuthEntity


    /**
     * Регистрация
     **/
    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): AuthEntity


    /**
     * Восстановление пароля
     **/
    @POST("remember_password")
    suspend fun recoverPassword(
        @Body request: RecoverRequest
    ): Boolean


    /**
     * Пользователь
     **/
    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") id: Int
    ): UserEntity


    /**
     * Пользовательские пароли
     **/
    @GET("passwords")
    suspend fun getLocationPasswords(
        @Query("take") take: Int = Constants.API_DEFAULT_TAKE,
        @Query("page") page: Int = 0,
        @Query("user") userId: Int
    ): List<LocationPasswordEntity>

    @POST("passwords")
    suspend fun addLocationPassword(
        @Body request: AddLocationPasswordRequest
    ): Boolean

    @PUT("passwords")
    suspend fun updateLocationPassword(
        @Body request: UpdateLocationPasswordRequest
    ): Boolean

    @DELETE("passwords")
    suspend fun deleteLocationPassword(
        @Body request: DeleteLocationPasswordRequest
    ): Boolean


    /**
     * Отслеживаемые пароли
     **/
    @GET("tracking")
    suspend fun getTrackingPasswords(
        @Query("take") take: Int = Constants.API_DEFAULT_TAKE,
        @Query("page") page: Int = 0,
        @Query("user") userId: Int
    ): List<TrackingPasswordEntity>

    @POST("tracking")
    suspend fun addTrackingPassword(
        @Body request: AddTrackingPasswordRequest
    ): Boolean

    @PUT("tracking")
    suspend fun updateTrackingPassword(
        @Body request: UpdateTrackingPasswordRequest
    ): Boolean

    @DELETE("tracking")
    suspend fun deleteTrackingPassword(
        @Body request: DeleteTrackingPasswordRequest
    ): Boolean
}