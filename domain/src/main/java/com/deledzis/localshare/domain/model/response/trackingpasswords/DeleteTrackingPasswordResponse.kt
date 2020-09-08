package com.deledzis.localshare.domain.model.response.trackingpasswords

import com.deledzis.localshare.domain.model.entity.Entity

data class DeleteTrackingPasswordResponse(
    val result: Boolean
) : Entity()