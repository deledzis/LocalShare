package com.deledzis.localshare.data.repository.auth

import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.data.model.AuthEntity
import com.deledzis.localshare.data.model.UserEntity

/**
 * Interface defining methods for the data operations related to [AuthEntity].
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface AuthDataStore {

    suspend fun authUser(email: String, password: String): Response<AuthEntity, Error>

    suspend fun verifyToken(token: String): Response<AuthEntity, Error>

    suspend fun refreshToken(token: String): Response<AuthEntity, Error>

    suspend fun register(email: String, password: String): Response<AuthEntity, Error>

    suspend fun recoverPassword(email: String): Response<Boolean, Error>

    suspend fun getUser(id: Int): Response<UserEntity, Error>

}