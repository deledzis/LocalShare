package com.deledzis.localshare.cache.db.mapper

import com.deledzis.localshare.cache.db.model.CachedTrackingPassword
import com.deledzis.localshare.data.mapper.LastCoordinatesMapper
import com.deledzis.localshare.data.model.TrackingPasswordEntity
import javax.inject.Inject

class TrackingPasswordEntityMapper @Inject constructor(
    private val lastCoordinatesMapper: LastCoordinatesMapper
) :
    EntityMapper<CachedTrackingPassword, TrackingPasswordEntity> {
    override fun mapFromCached(type: CachedTrackingPassword): TrackingPasswordEntity {
        return TrackingPasswordEntity(
            id = type.id,
            password = type.password,
            description = type.description,
            active = type.active,
            lastCoordinates = lastCoordinatesMapper.mapToEntity(
                type = type.lastCoordinates
            ),
            lastUpdateTime = type.lastUpdateTime
        )
    }

    override fun mapToCached(type: TrackingPasswordEntity): CachedTrackingPassword {
        return CachedTrackingPassword(
            id = type.id,
            password = type.password,
            description = type.description,
            active = type.active,
            lastCoordinates = lastCoordinatesMapper.mapFromEntity(
                type = type.lastCoordinates
            ),
            lastUpdateTime = type.lastUpdateTime
        )
    }

}