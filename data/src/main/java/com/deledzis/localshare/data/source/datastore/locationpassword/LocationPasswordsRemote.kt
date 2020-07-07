package com.deledzis.localshare.data.source.datastore.locationpassword

import com.deledzis.localshare.data.source.server.model.LocationPasswordEntity

/**
 * Interface defining methods for the caching of [LocationPasswordEntity].
 * This is to be implemented by the external data layer,
 * using this interface as a way of communicating.
 */
interface LocationPasswordsRemote {

    /**
     * Retrieve a list of [LocationPasswordEntity], from the remote service
     */
    suspend fun getLocationPasswords(userId: Int): List<LocationPasswordEntity>

    /**
     * Create new [LocationPasswordEntity] instance.
     */
    suspend fun addLocationPassword(password: String, description: String): Boolean

    /**
     * Update an existing instance of [LocationPasswordEntity].
     */
    suspend fun updateLocationPassword(locationPasswordEntity: LocationPasswordEntity): Boolean

    /**
     * Delete an existing instance of [LocationPasswordEntity].
     */
    suspend fun deleteLocationPassword(id: Int): Boolean
}