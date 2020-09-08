package com.deledzis.localshare.domain.model.response.locationpasswords

import com.deledzis.localshare.domain.model.entity.Entity

data class UpdateLocationPasswordResponse(
    val result: Boolean
) : Entity()