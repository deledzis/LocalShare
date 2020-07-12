package com.deledzis.localshare.domain.model.entity.auth

import com.deledzis.localshare.domain.model.entity.Entity

data class ForgetPasswordResponse(
    val result: Boolean
) : Entity()