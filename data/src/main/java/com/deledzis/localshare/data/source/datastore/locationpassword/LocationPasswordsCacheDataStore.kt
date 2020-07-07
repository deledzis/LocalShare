package com.deledzis.localshare.data.source.datastore.locationpassword

import com.deledzis.localshare.data.source.server.model.LocationPasswordEntity
import javax.inject.Inject

/**
 * Implementation of the [LocationPasswordsDataStore] interface to provide a means of communicating
 * with the local data source
 */
open class LocationPasswordsCacheDataStore @Inject constructor(
    private val locationPasswordsCache: LocationPasswordsCache
) : LocationPasswordsDataStore {

    /**
     * Retrieve a list of [LocationPasswordEntity] instance from the cache
     */
    override suspend fun getLocationPasswords(userId: Int): List<LocationPasswordEntity> {
        return locationPasswordsCache.getLocationPasswords(userId = userId)
    }

    override suspend fun addLocationPassword(password: String, description: String): Boolean {
        val insertedRows = locationPasswordsCache.addLocationPassword(
            password = password,
            description = description
        )
        return insertedRows > 0
    }

    override suspend fun updateLocationPassword(locationPassword: LocationPasswordEntity): Boolean {
        val updatedRows = locationPasswordsCache.updateLocationPassword(
            locationPasswordEntity = locationPassword
        )
        return updatedRows > 0
    }

    override suspend fun deleteLocationPassword(id: Int): Boolean {
        val deletedRows = locationPasswordsCache.deleteLocationPassword(
            id = id
        )
        return deletedRows > 0
    }

    /**
     * Clear all [LocationPasswordEntity] from the cache
     */
    override suspend fun clearLocationPasswords() {
        return locationPasswordsCache.clearLocationPasswords()
    }

    /**
     * Save a given [List] of [LocationPasswordEntity] instances to the cache
     */
    override suspend fun saveLocationPasswords(locationPasswords: List<LocationPasswordEntity>) {
        locationPasswordsCache.saveLocationPasswords(locationPasswords)
        locationPasswordsCache.setLastCacheTime(System.currentTimeMillis())
    }

}