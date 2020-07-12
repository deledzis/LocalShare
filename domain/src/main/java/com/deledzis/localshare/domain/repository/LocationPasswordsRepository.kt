package com.deledzis.localshare.domain.repository

import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.LocationPassword
import com.deledzis.localshare.domain.usecase.None

/**
 * Interface defining methods for how the Data layer can pass data to and from the Domain layer.
 * This is to be implemented by the Data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface LocationPasswordsRepository {

    suspend fun getLocationPasswords(userId: Int): Response<List<LocationPassword>, Error>

    suspend fun addLocationPassword(password: String, description: String): Response<Boolean, Error>

    suspend fun updateLocationPassword(locationPassword: LocationPassword): Response<Boolean, Error>

    suspend fun deleteLocationPassword(id: Int): Response<Boolean, Error>

    suspend fun clearLocationPasswords(): Response<None, Error>

    suspend fun saveLocationPasswords(locationPasswords: List<LocationPassword>): Response<None, Error>

}