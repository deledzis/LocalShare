package com.deledzis.localshare.data.repository.trackingpasswords

import com.deledzis.localshare.data.model.TrackingPasswordEntity

/**
 * Interface defining methods for the caching of [TrackingPasswordEntity].
 * This is to be implemented by the cache layer, using this interface as a way of communicating.
 */
interface TrackingPasswordsCache {

    /**
     * Retrieve a list of [TrackingPasswordEntity], from the cache
     */
    suspend fun getTrackingPasswords(): List<TrackingPasswordEntity>

    /**
     * Update an existing instance of [TrackingPasswordEntity] in cache.
     */
    suspend fun updateTrackingPassword(trackingPasswordEntity: TrackingPasswordEntity): Int

    /**
     * Delete an existing instance of [TrackingPasswordEntity] from cache.
     */
    suspend fun deleteTrackingPassword(password: String): Int

    /**
     * Clear all [TrackingPasswordEntity] from the cache
     */
    suspend fun clearTrackingPasswords()

    /**
     * Save a given list of [TrackingPasswordEntity] to the cache
     */
    suspend fun saveTrackingPasswords(trackingPasswords: List<TrackingPasswordEntity>)

    /**
     * Save an instance of [TrackingPasswordEntity] to the cache
     */
    suspend fun saveTrackingPassword(trackingPasswordEntity: TrackingPasswordEntity)

    /**
     * Checks if an element [TrackingPasswordEntity] exists in the cache.

     * @param password The value of [TrackingPasswordEntity] to look for inside the cache.
     * *
     * @return true if the element is cached, otherwise false.
     */
    suspend fun isCached(password: String): Boolean

    /**
     * Sets last cache time
     */
    suspend fun setLastCacheTime(lastCache: Long)

    /**
     * Checks if the cache is expired.

     * @return true, the cache is expired, otherwise false.
     */
    suspend fun isExpired(): Boolean

}