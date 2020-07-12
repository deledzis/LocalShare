package com.deledzis.localshare.data.mapper

import com.deledzis.localshare.data.model.UserEntity
import com.deledzis.localshare.domain.model.User
import javax.inject.Inject

class UserMapper @Inject constructor() : Mapper<UserEntity, User> {
    override fun mapFromEntity(type: UserEntity): User {
        return User(
            id = type.id,
            email = type.email,
            token = type.token
        )
    }

    override fun mapToEntity(type: User): UserEntity {
        return UserEntity(
            id = type.id,
            email = type.email,
            token = type.token ?: ""
        )
    }

}