package com.deledzis.localshare.domain.model.response.auth

import com.deledzis.localshare.domain.model.User
import com.deledzis.localshare.domain.model.entity.Entity

data class GetUserResponse(
    val user: User
) : Entity()