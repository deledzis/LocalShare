package com.deledzis.localshare.domain.model.response.locationpasswords

import com.deledzis.localshare.domain.model.LocationPassword
import com.deledzis.localshare.domain.model.entity.Entity

data class GetLocationPasswordsResponse(
    val items: List<LocationPassword>
) : Entity()