package com.deledzis.localshare.data.source.server.mapper

import com.deledzis.localshare.data.source.server.model.UserEntity
import com.deledzis.localshare.domain.model.User
import javax.inject.Inject

class UserMapper @Inject constructor() : Mapper<UserEntity, User> {
    override fun mapFromEntity(type: UserEntity): User {
        return User(
            id = type.id,
            firstName = type.firstName,
            lastName = type.lastName
        )
    }

    override fun mapToEntity(type: User): UserEntity {
        return UserEntity(
            id = type.id,
            firstName = type.firstName,
            lastName = type.lastName
        )
    }

}