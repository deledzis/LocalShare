package com.deledzis.localshare.domain.model.response.trackingpasswords

import com.deledzis.localshare.domain.model.TrackingPassword
import com.deledzis.localshare.domain.model.entity.Entity

data class GetTrackingPasswordsResponse(
    val items: List<TrackingPassword>
) : Entity()