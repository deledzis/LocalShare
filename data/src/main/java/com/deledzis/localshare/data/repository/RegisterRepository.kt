package com.deledzis.localshare.data.repository

import com.deledzis.localshare.common.BaseNetworkManager
import com.deledzis.localshare.data.source.server.ApiRemote
import com.deledzis.localshare.data.source.server.mapper.AuthMapper
import com.deledzis.localshare.data.source.server.mapper.UserMapper
import com.deledzis.localshare.domain.model.Auth
import com.deledzis.localshare.domain.model.User
import com.deledzis.localshare.domain.repository.IRegisterRepository
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    apiRemote: ApiRemote,
    networkManager: BaseNetworkManager,
    private val authMapper: AuthMapper,
    private val userMapper: UserMapper
) : BaseRepository(apiRemote = apiRemote, networkManager = networkManager), IRegisterRepository {

    override suspend fun register(email: String, password: String): Auth? {
        val entity = safeApiCall(
            call = { apiRemote.register(email = email, password = password) },
            errorMessage = "Error when try to register user"
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