package com.deledzis.localshare.data.mapper

import com.deledzis.localshare.data.model.AuthEntity
import com.deledzis.localshare.domain.model.Auth
import javax.inject.Inject

class AuthMapper @Inject constructor() : Mapper<AuthEntity, Auth> {
    override fun mapFromEntity(type: AuthEntity): Auth {
        return Auth(
            token = type.token,
            userId = type.userId
        )
    }

    override fun mapToEntity(type: Auth): AuthEntity {
        return AuthEntity(
            token = type.token,
            userId = type.userId
        )
    }

}