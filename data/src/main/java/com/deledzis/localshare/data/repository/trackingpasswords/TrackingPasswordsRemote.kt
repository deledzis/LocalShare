package com.deledzis.localshare.data.repository.trackingpasswords

import com.deledzis.localshare.data.model.TrackingPasswordEntity


/**
 * Interface defining methods for the caching of [TrackingPasswordEntity].
 * This is to be implemented by the external data layer,
 * using this interface as a way of communicating.
 */
interface TrackingPasswordsRemote {

    /**
     * Retrieve a list of [TrackingPasswordEntity], from the remote service
     */
    suspend fun getTrackingPasswords(userId: Int): List<TrackingPasswordEntity>

    /**
     * Create new [TrackingPasswordEntity] instance.
     */
    suspend fun addTrackingPassword(password: String, description: String): Boolean

    /**
     * Update an existing instance of [TrackingPasswordEntity].
     */
    suspend fun updateTrackingPassword(trackingPasswordEntity: TrackingPasswordEntity): Boolean

    /**
     * Delete an existing instance of [TrackingPasswordEntity].
     */
    suspend fun deleteTrackingPassword(password: String): Boolean
}