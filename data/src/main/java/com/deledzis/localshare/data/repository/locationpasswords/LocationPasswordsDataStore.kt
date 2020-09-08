package com.deledzis.localshare.data.repository.locationpasswords

import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.data.model.LocationPasswordEntity
import com.deledzis.localshare.domain.usecase.None

/**
 * Interface defining methods for the data operations related to [LocationPasswordEntity].
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface LocationPasswordsDataStore {

    suspend fun getLocationPasswords(userId: Int): Response<List<LocationPasswordEntity>, Error>

    suspend fun addLocationPassword(password: String, description: String): Response<Boolean, Error>

    suspend fun updateLocationPassword(locationPassword: LocationPasswordEntity): Response<Boolean, Error>

    suspend fun deleteLocationPassword(password: String): Response<Boolean, Error>

    suspend fun clearLocationPasswords(): Response<None, Error>

    suspend fun saveLocationPasswords(locationPasswords: List<LocationPasswordEntity>): Response<None, Error>

}