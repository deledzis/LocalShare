package com.deledzis.localshare.remote

import com.deledzis.localshare.data.source.server.ApiRemote
import com.deledzis.localshare.data.source.server.model.AuthEntity
import com.deledzis.localshare.data.source.server.model.LocationPasswordEntity
import com.deledzis.localshare.data.source.server.model.UserEntity
import com.deledzis.localshare.remote.model.*
import retrofit2.Response
import javax.inject.Inject

/**
 * Remote implementation for retrieving [LocationPasswordEntity] instances. This class implements the
 * [ApiRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class ApiRemoteImpl @Inject constructor(
    private val apiService: ApiService
) : ApiRemote {

    /**
     * Try to authenticate user by given credentials.
     * In case of success, retrieves an instance of [AuthEntity] from the [ApiService].
     */
    override suspend fun auth(email: String, password: String): Response<AuthEntity> {
        return apiService.auth(
            request = AuthUserRequest(
                email = email,
                password = password
            )
        )
    }

    /**
     * Verifying user access token on a server
     * and retrieve an instance of [AuthEntity] model from the [ApiService].
     */
    override suspend fun verifyToken(token: String): Response<AuthEntity> {
        return apiService.verifyToken(
            request = VerifyTokenRequest(
                token = token
            )
        )
    }

    /**
     * Try to refresh user access token
     * and retrieve refreshed [AuthEntity] model from the [ApiService].
     */
    override suspend fun refreshToken(token: String): Response<AuthEntity> {
        return apiService.refreshToken(
            request = RefreshTokenRequest(
                token = token
            )
        )
    }

    /**
     * Try to register user on a server
     * and retrieve an instance of [AuthEntity] model from the [ApiService].
     */
    override suspend fun register(email: String, password: String): Response<AuthEntity> {
        return apiService.register(
            request = RegisterRequest(
                email = email,
                password = password
            )
        )
    }

    /**
     * Invokes a password recovery process on server.
     */
    override suspend fun recoverPassword(email: String): Response<Boolean> {
        return apiService.recoverPassword(
            request = RecoverRequest(
                email = email
            )
        )
    }

    /**
     * Retrieve an instance of [UserEntity] from the [ApiService].
     */
    override suspend fun getUser(id: Int): Response<UserEntity> {
        return apiService.getUser(id = id)
    }

    /**
     * Retrieve a list of [LocationPasswordEntity] instances from the [ApiService].
     */
    override suspend fun getLocationPasswords(userId: Int): Response<List<LocationPasswordEntity>> {
        return apiService.getLocationPasswords(userId = userId)
    }

    /**
     * Create new [LocationPasswordEntity] instance.
     */
    override suspend fun addLocationPassword(password: String, description: String): Response<Boolean> {
        return apiService.addLocationPassword(
            request = AddLocationPasswordRequest(
                password = password,
                description = description
            )
        )
    }

    /**
     * Update an existing instance of [LocationPasswordEntity].
     */
    override suspend fun updateLocationPassword(locationPasswordEntity: LocationPasswordEntity): Response<Boolean> {
        return apiService.updateLocationPassword(
            request = UpdateLocationPasswordRequest(
                id = locationPasswordEntity.id,
                password = locationPasswordEntity.password,
                description = locationPasswordEntity.description
            )
        )
    }

    /**
     * Delete an existing instance of [LocationPasswordEntity].
     */
    override suspend fun deleteLocationPassword(id: Int): Response<Boolean> {
        return apiService.deleteLocationPassword(
            request = DeleteLocationPasswordRequest(
                id = id
            )
        )
    }

}