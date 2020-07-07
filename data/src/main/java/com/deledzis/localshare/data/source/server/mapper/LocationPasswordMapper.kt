package com.deledzis.localshare.data.source.server.mapper

import com.deledzis.localshare.data.source.server.model.LocationPasswordEntity
import com.deledzis.localshare.domain.model.LocationPassword
import javax.inject.Inject

class LocationPasswordMapper @Inject constructor() : Mapper<LocationPasswordEntity, LocationPassword> {
    override fun mapFromEntity(type: LocationPasswordEntity): LocationPassword {
        return LocationPassword(
            id = type.id,
            password = type.password,
            description = type.description
        )
    }

    override fun mapToEntity(type: LocationPassword): LocationPasswordEntity {
        return LocationPasswordEntity(
            id = type.id,
            password = type.password,
            description = type.description
        )
    }
}