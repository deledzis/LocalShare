package com.deledzis.localshare.remote

import com.deledzis.localshare.data.model.TrackingPasswordEntity
import com.deledzis.localshare.data.repository.trackingpasswords.TrackingPasswordsRemote
import com.deledzis.localshare.remote.model.AddTrackingPasswordRequest
import com.deledzis.localshare.remote.model.DeleteTrackingPasswordRequest
import com.deledzis.localshare.remote.model.UpdateTrackingPasswordRequest
import javax.inject.Inject

/**
 * Remote implementation for retrieving [TrackingPasswordEntity] instances. This class implements the
 * [TrackingPasswordsRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class TrackingPasswordsRemoteImpl @Inject constructor(
    private val apiService: ApiService
) : TrackingPasswordsRemote {

    /**
     * Retrieve a list of [TrackingPasswordEntity] instances from the [ApiService].
     */
    override suspend fun getTrackingPasswords(userId: Int): List<TrackingPasswordEntity> {
        return apiService.getTrackingPasswords(userId = userId)
    }

    /**
     * Create new [TrackingPasswordEntity] instance.
     */
    override suspend fun addTrackingPassword(password: String, description: String): Boolean {
        return apiService.addTrackingPassword(
            request = AddTrackingPasswordRequest(
                password = password,
                description = description
            )
        )
    }

    /**
     * Update an existing instance of [TrackingPasswordEntity].
     */
    override suspend fun updateTrackingPassword(trackingPasswordEntity: TrackingPasswordEntity): Boolean {
        return apiService.updateTrackingPassword(
            request = UpdateTrackingPasswordRequest(
                description = trackingPasswordEntity.description
            )
        )
    }

    /**
     * Delete an existing instance of [TrackingPasswordEntity].
     */
    override suspend fun deleteTrackingPassword(password: String): Boolean {
        return apiService.deleteTrackingPassword(
            request = DeleteTrackingPasswordRequest(password = password)
        )
    }

}