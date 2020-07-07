package com.deledzis.localshare.data.source.datastore.locationpassword

import com.deledzis.localshare.data.source.server.model.LocationPasswordEntity
import javax.inject.Inject

/**
 * Implementation of the [LocationPasswordsDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class LocationPasswordsRemoteDataStore @Inject constructor(private val locationPasswordsRemote: LocationPasswordsRemote) :
    LocationPasswordsDataStore {

    override suspend fun getLocationPasswords(userId: Int): List<LocationPasswordEntity> {
        return locationPasswordsRemote.getLocationPasswords(userId = userId)
    }

    override suspend fun addLocationPassword(password: String, description: String): Boolean {
        return locationPasswordsRemote.addLocationPassword(
            password = password,
            description = description
        )
    }

    override suspend fun updateLocationPassword(locationPassword: LocationPasswordEntity): Boolean {
        return locationPasswordsRemote.updateLocationPassword(
            locationPasswordEntity = locationPassword
        )
    }

    override suspend fun deleteLocationPassword(id: Int): Boolean {
        return locationPasswordsRemote.deleteLocationPassword(
            id = id
        )
    }

    override suspend fun clearLocationPasswords() {
        throw UnsupportedOperationException()
    }

    override suspend fun saveLocationPasswords(locationPasswords: List<LocationPasswordEntity>) {
        throw UnsupportedOperationException()
    }

}