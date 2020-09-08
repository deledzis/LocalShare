package com.deledzis.localshare.data.mapper

import com.deledzis.localshare.data.model.LastCoordinatesEntity
import com.deledzis.localshare.domain.model.LastCoordinates
import javax.inject.Inject

class LastCoordinatesMapper @Inject constructor() : Mapper<LastCoordinatesEntity, LastCoordinates> {
    override fun mapFromEntity(type: LastCoordinatesEntity): LastCoordinates {
        return LastCoordinates(
            lat = type.lat,
            lng = type.lng
        )
    }

    override fun mapToEntity(type: LastCoordinates): LastCoordinatesEntity {
        return LastCoordinatesEntity(
            lat = type.lat,
            lng = type.lng
        )
    }
}