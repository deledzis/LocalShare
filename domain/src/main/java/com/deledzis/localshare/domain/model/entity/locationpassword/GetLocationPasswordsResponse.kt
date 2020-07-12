package com.deledzis.localshare.domain.model.entity.locationpassword

import com.deledzis.localshare.domain.model.LocationPassword
import com.deledzis.localshare.domain.model.entity.Entity

data class GetLocationPasswordsResponse(
    val items: List<LocationPassword>
) : Entity()