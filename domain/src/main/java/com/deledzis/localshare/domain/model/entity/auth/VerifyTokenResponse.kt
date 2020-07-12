package com.deledzis.localshare.domain.model.entity.auth

import com.deledzis.localshare.domain.model.Auth
import com.deledzis.localshare.domain.model.entity.Entity

data class VerifyTokenResponse(
    val auth: Auth
) : Entity()