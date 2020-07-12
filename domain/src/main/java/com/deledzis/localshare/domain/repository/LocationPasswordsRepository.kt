package com.deledzis.localshare.domain.repository

import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.LocationPassword
import com.deledzis.localshare.domain.model.entity.locationpassword.*

/**
 * Interface defining methods for how the Data layer can pass data to and from the Domain layer.
 * This is to be implemented by the Data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface LocationPasswordsRepository {

    suspend fun getLocationPasswords(
        userId: Int,
        refresh: Boolean
    ): Response<GetLocationPasswordsResponse, Error>

    suspend fun addLocationPassword(
        password: String,
        description: String
    ): Response<AddLocationPasswordResponse, Error>

    suspend fun updateLocationPassword(
        locationPassword: LocationPassword
    ): Response<UpdateLocationPasswordResponse, Error>

    suspend fun deleteLocationPassword(
        password: String
    ): Response<DeleteLocationPasswordResponse, Error>

    suspend fun clearLocationPasswords(): Response<ClearLocationPasswordsResponse, Error>

    suspend fun saveLocationPasswords(
        locationPasswords: List<LocationPassword>
    ): Response<SaveLocationPasswordsResponse, Error>

}