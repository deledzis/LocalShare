package com.deledzis.localshare.cache.db.mapper

import com.deledzis.localshare.cache.db.model.CachedLocationPassword
import com.deledzis.localshare.data.model.LocationPasswordEntity
import javax.inject.Inject

class LocationPasswordEntityMapper @Inject constructor() :
    EntityMapper<CachedLocationPassword, LocationPasswordEntity> {
    override fun mapFromCached(type: CachedLocationPassword): LocationPasswordEntity {
        return LocationPasswordEntity(
            id = type.id,
            password = type.password,
            description = type.description,
            active = type.active,
            ownerId = type.ownerId
        )
    }

    override fun mapToCached(type: LocationPasswordEntity): CachedLocationPassword {
        return CachedLocationPassword(
            id = type.id,
            password = type.password,
            description = type.description,
            active = type.active,
            ownerId = type.ownerId
        )
    }

}