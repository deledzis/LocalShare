package com.deledzis.localshare.domain.model.response.auth

import com.deledzis.localshare.domain.model.entity.Entity

data class ForgetPasswordResponse(
    val result: Boolean
) : Entity()