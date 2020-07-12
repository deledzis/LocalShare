package com.deledzis.localshare.cache.mapper

import com.deledzis.localshare.cache.preferences.user.AuthenticatedUser
import com.deledzis.localshare.domain.model.User
import javax.inject.Inject

class UserMapper @Inject constructor() : Mapper<AuthenticatedUser, User> {
    override fun mapFromEntity(type: AuthenticatedUser): User {
        return User(
            id = type.id,
            firstName = type.firstName,
            lastName = type.lastName,
            token = type.token
        )
    }

    override fun mapToEntity(type: User): AuthenticatedUser {
        return AuthenticatedUser(
            id = type.id,
            firstName = type.firstName,
            lastName = type.lastName,
            token = type.token ?: ""
        )
    }

}