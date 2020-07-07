package com.deledzis.localshare.data.source.datastore.locationpassword

import com.deledzis.localshare.data.source.server.model.LocationPasswordEntity

/**
 * Interface defining methods for the caching of [LocationPasswordEntity].
 * This is to be implemented by the cache layer, using this interface as a way of communicating.
 */
interface LocationPasswordsCache {

    /**
     * Retrieve a list of [LocationPasswordEntity], from the cache
     */
    suspend fun getLocationPasswords(userId: Int): List<LocationPasswordEntity>

    /**
     * Add new [LocationPasswordEntity] instance to the cache.
     */
    suspend fun addLocationPassword(password: String, description: String): Int

    /**
     * Update an existing instance of [LocationPasswordEntity] in cache.
     */
    suspend fun updateLocationPassword(locationPasswordEntity: LocationPasswordEntity): Int

    /**
     * Delete an existing instance of [LocationPasswordEntity] from cache.
     */
    suspend fun deleteLocationPassword(id: Int): Int

    /**
     * Clear all [LocationPasswordEntity] from the cache
     */
    suspend fun clearLocationPasswords()

    /**
     * Save a given list of [LocationPasswordEntity] to the cache
     */
    suspend fun saveLocationPasswords(searchResults: List<LocationPasswordEntity>)

    /**
     * Checks if an element [LocationPasswordEntity] exists in the cache.

     * @param id The id of [LocationPasswordEntity] to look for inside the cache.
     * *
     * @return true if the element is cached, otherwise false.
     */
    suspend fun isCached(id: Int): Boolean

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