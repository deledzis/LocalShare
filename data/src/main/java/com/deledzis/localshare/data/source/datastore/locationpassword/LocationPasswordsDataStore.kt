package com.deledzis.localshare.data.source.datastore.locationpassword

import com.deledzis.localshare.data.source.server.model.LocationPasswordEntity

/**
 * Interface defining methods for the data operations related to [LocationPasswordEntity].
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface LocationPasswordsDataStore {

    suspend fun getLocationPasswords(userId: Int): List<LocationPasswordEntity>

    suspend fun addLocationPassword(password: String, description: String): Boolean

    suspend fun updateLocationPassword(locationPassword: LocationPasswordEntity): Boolean

    suspend fun deleteLocationPassword(id: Int): Boolean

    suspend fun clearLocationPasswords()

    suspend fun saveLocationPasswords(locationPasswords: List<LocationPasswordEntity>)

}