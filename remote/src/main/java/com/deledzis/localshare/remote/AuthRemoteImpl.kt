package com.deledzis.localshare.remote

import com.deledzis.localshare.data.model.AuthEntity
import com.deledzis.localshare.data.model.UserEntity
import com.deledzis.localshare.data.repository.auth.AuthRemote
import com.deledzis.localshare.remote.model.*
import javax.inject.Inject

class AuthRemoteImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRemote {

    /**
     * Try to authenticate user by given credentials.
     * In case of success, retrieves an instance of [AuthEntity] from the [ApiService].
     */
    override suspend fun auth(email: String, password: String): AuthEntity {
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
    override suspend fun verifyToken(token: String): AuthEntity {
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
    override suspend fun refreshToken(token: String): AuthEntity {
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
    override suspend fun register(email: String, password: String): AuthEntity {
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
    override suspend fun recoverPassword(email: String): Boolean {
        return apiService.recoverPassword(
            request = RecoverRequest(
                email = email
            )
        )
    }

    /**
     * Retrieve an instance of [UserEntity] from the [ApiService].
     */
    override suspend fun getUser(id: Int): UserEntity {
        return apiService.getUser(id = id)
    }

}