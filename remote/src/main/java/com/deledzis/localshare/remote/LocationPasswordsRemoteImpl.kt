package com.deledzis.localshare.remote

import com.deledzis.localshare.data.model.LocationPasswordEntity
import com.deledzis.localshare.data.repository.locationpassword.LocationPasswordsRemote
import com.deledzis.localshare.remote.model.AddLocationPasswordRequest
import com.deledzis.localshare.remote.model.DeleteLocationPasswordRequest
import com.deledzis.localshare.remote.model.UpdateLocationPasswordRequest
import javax.inject.Inject

/**
 * Remote implementation for retrieving [LocationPasswordEntity] instances. This class implements the
 * [LocationPasswordsRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class LocationPasswordsRemoteImpl @Inject constructor(
    private val apiService: ApiService
) : LocationPasswordsRemote {

    /**
     * Retrieve a list of [LocationPasswordEntity] instances from the [ApiService].
     */
    override suspend fun getLocationPasswords(userId: Int): List<LocationPasswordEntity> {
        return apiService.getLocationPasswords(userId = userId)
    }

    /**
     * Create new [LocationPasswordEntity] instance.
     */
    override suspend fun addLocationPassword(password: String, description: String): Boolean {
        return apiService.addLocationPassword(
            request = AddLocationPasswordRequest(
                password = password,
                description = description
            )
        )
    }

    /**
     * Update an existing instance of [LocationPasswordEntity].
     */
    override suspend fun updateLocationPassword(locationPasswordEntity: LocationPasswordEntity): Boolean {
        return apiService.updateLocationPassword(
            request = UpdateLocationPasswordRequest(
                id = locationPasswordEntity.id,
                password = locationPasswordEntity.password,
                description = locationPasswordEntity.description
            )
        )
    }

    /**
     * Delete an existing instance of [LocationPasswordEntity].
     */
    override suspend fun deleteLocationPassword(id: Int): Boolean {
        return apiService.deleteLocationPassword(
            request = DeleteLocationPasswordRequest(id = id)
        )
    }

}