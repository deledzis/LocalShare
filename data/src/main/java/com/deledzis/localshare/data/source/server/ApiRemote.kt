package com.deledzis.localshare.data.source.server

import com.deledzis.localshare.data.source.server.model.AuthEntity
import com.deledzis.localshare.data.source.server.model.LocationPasswordEntity
import com.deledzis.localshare.data.source.server.model.UserEntity
import retrofit2.Response

interface ApiRemote {
    suspend fun auth(email: String, password: String): Response<AuthEntity>

    suspend fun verifyToken(token: String): Response<AuthEntity>

    suspend fun refreshToken(token: String): Response<AuthEntity>

    suspend fun register(email: String, password: String): Response<AuthEntity>

    suspend fun recoverPassword(email: String): Response<Boolean>

    suspend fun getUser(id: Int): Response<UserEntity>

    suspend fun getLocationPasswords(userId: Int): Response<List<LocationPasswordEntity>>

    suspend fun addLocationPassword(password: String, description: String): Response<Boolean>

    suspend fun updateLocationPassword(locationPasswordEntity: LocationPasswordEntity): Response<Boolean>

    suspend fun deleteLocationPassword(id: Int): Response<Boolean>
}