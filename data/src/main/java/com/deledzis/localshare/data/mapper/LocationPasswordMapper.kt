package com.deledzis.localshare.data.mapper

import com.deledzis.localshare.data.model.LocationPasswordEntity
import com.deledzis.localshare.domain.model.LocationPassword
import javax.inject.Inject

class LocationPasswordMapper @Inject constructor() : Mapper<LocationPasswordEntity, LocationPassword> {
    override fun mapFromEntity(type: LocationPasswordEntity): LocationPassword {
        return LocationPassword(
            password = type.password,
            description = type.description,
            active = type.active,
            ownerId = type.ownerId
        )
    }

    override fun mapToEntity(type: LocationPassword): LocationPasswordEntity {
        return LocationPasswordEntity(
            password = type.password,
            description = type.description,
            active = type.active,
            ownerId = type.ownerId
        )
    }
}