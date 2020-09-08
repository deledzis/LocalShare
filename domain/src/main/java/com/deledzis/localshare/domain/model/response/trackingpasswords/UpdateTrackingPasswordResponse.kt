package com.deledzis.localshare.domain.model.response.trackingpasswords

import com.deledzis.localshare.domain.model.entity.Entity

data class UpdateTrackingPasswordResponse(
    val result: Boolean
) : Entity()