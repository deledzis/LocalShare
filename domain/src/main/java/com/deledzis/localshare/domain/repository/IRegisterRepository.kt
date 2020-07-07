package com.deledzis.localshare.domain.repository

import com.deledzis.localshare.domain.model.Auth
import com.deledzis.localshare.domain.model.User

interface IRegisterRepository {
    suspend fun register(email: String, password: String): Auth?

    suspend fun getUser(id: Int): User?
}