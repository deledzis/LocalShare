package com.deledzis.localshare.data.mapper

import com.deledzis.localshare.data.model.TrackingPasswordEntity
import com.deledzis.localshare.domain.model.TrackingPassword
import javax.inject.Inject

class TrackingPasswordMapper @Inject constructor(
    private val lastCoordinatesMapper: LastCoordinatesMapper
) : Mapper<TrackingPasswordEntity, TrackingPassword> {
    override fun mapFromEntity(type: TrackingPasswordEntity): TrackingPassword {
        return TrackingPassword(
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

    override fun mapToEntity(type: TrackingPassword): TrackingPasswordEntity {
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
}