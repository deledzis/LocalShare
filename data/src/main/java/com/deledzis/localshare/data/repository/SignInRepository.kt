package com.deledzis.localshare.data.repository

import com.deledzis.localshare.common.BaseNetworkManager
import com.deledzis.localshare.data.source.server.ApiRemote
import com.deledzis.localshare.data.source.server.mapper.AuthMapper
import com.deledzis.localshare.data.source.server.mapper.UserMapper
import com.deledzis.localshare.domain.model.Auth
import com.deledzis.localshare.domain.model.User
import com.deledzis.localshare.domain.repository.ISignInRepository
import javax.inject.Inject

class SignInRepository @Inject constructor(
    apiRemote: ApiRemote,
    networkManager: BaseNetworkManager,
    private val authMapper: AuthMapper,
    private val userMapper: UserMapper
) : BaseRepository(apiRemote = apiRemote, networkManager = networkManager), ISignInRepository {
    override suspend fun auth(email: String, password: String): Auth? {
        val entity = safeApiCall(
            call = { apiRemote.auth(email = email, password = password) },
            errorMessage = "Error when try to auth user"
        ) ?: return null

        return authMapper.mapFromEntity(entity)
    }

    override suspend fun verifyToken(token: String): Auth? {
        val entity = safeApiCall(
            call = { apiRemote.verifyToken(token = token) },
            errorMessage = "Error when try to verify user access token"
        ) ?: return null

        return authMapper.mapFromEntity(entity)
    }

    override suspend fun refreshToken(token: String): Auth? {
        val entity = safeApiCall(
            call = { apiRemote.refreshToken(token = token) },
            errorMessage = "Error when try to refresh user access token"
        ) ?: return null

        return authMapper.mapFromEntity(entity)
    }

    override suspend fun getUser(id: Int): User? {
        val entity = safeApiCall(
            call = { apiRemote.getUser(id = id) },
            errorMessage = "Error when try to get User by ID = $id"
        ) ?: return null

        return userMapper.mapFromEntity(entity)
    }

}