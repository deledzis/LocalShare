package com.deledzis.localshare.data.repository.auth

import com.deledzis.localshare.data.model.AuthEntity
import com.deledzis.localshare.data.model.LocationPasswordEntity
import com.deledzis.localshare.data.model.UserEntity

/**
 * Interface defining methods for the caching of [LocationPasswordEntity].
 * This is to be implemented by the external data layer,
 * using this interface as a way of communicating.
 */
interface AuthRemote {

    /**
     * Авторизация
     **/
    // Авторизация
    suspend fun auth(email: String, password: String): AuthEntity

    // Проверить токен
    suspend fun verifyToken(token: String): AuthEntity

    // Обновить токен
    suspend fun refreshToken(token: String): AuthEntity

    /**
     * Регистрация
     **/
    suspend fun register(email: String, password: String): AuthEntity

    /**
     * Восстановление пароля
     **/
    suspend fun recoverPassword(email: String): Boolean

    /**
     * Пользователь
     **/
    suspend fun getUser(id: Int): UserEntity
}