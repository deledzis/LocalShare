package com.deledzis.localshare.data.source.locationpasswords

import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.data.model.LocationPasswordEntity
import com.deledzis.localshare.data.repository.locationpasswords.LocationPasswordsDataStore
import com.deledzis.localshare.data.repository.locationpasswords.LocationPasswordsRemote
import com.deledzis.localshare.domain.usecase.None
import javax.inject.Inject

/**
 * Implementation of the [LocationPasswordsDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class LocationPasswordsRemoteDataStore @Inject constructor(
    private val locationPasswordsRemote: LocationPasswordsRemote
) : LocationPasswordsDataStore {

    override suspend fun getLocationPasswords(userId: Int): Response<List<LocationPasswordEntity>, Error> {
        return try {
            val locationPasswordsFromRemote =
                locationPasswordsRemote.getLocationPasswords(userId = userId)
            Response.Success(successData = locationPasswordsFromRemote)
        } catch (e: Exception) {
            Response.Failure(Error.NetworkError(exception = e))
        }
    }

    override suspend fun addLocationPassword(
        password: String,
        description: String
    ): Response<Boolean, Error> {
        return try {
            val added = locationPasswordsRemote.addLocationPassword(
                password = password,
                description = description
            )
            Response.Success(successData = added)
        } catch (e: Exception) {
            Response.Failure(Error.NetworkError(exception = e))
        }
    }

    override suspend fun updateLocationPassword(locationPassword: LocationPasswordEntity): Response<Boolean, Error> {
        return try {
            val updated = locationPasswordsRemote.updateLocationPassword(
                locationPasswordEntity = locationPassword
            )
            Response.Success(successData = updated)
        } catch (e: Exception) {
            Response.Failure(Error.NetworkError(exception = e))
        }
    }

    override suspend fun deleteLocationPassword(password: String): Response<Boolean, Error> {
        return try {
            val deleted = locationPasswordsRemote.deleteLocationPassword(password = password)
            Response.Success(successData = deleted)
        } catch (e: Exception) {
            Response.Failure(Error.NetworkError(exception = e))
        }
    }

    override suspend fun clearLocationPasswords(): Response<None, Error> {
        return Response.Failure(Error.UnsupportedOperationError())
    }

    override suspend fun saveLocationPasswords(locationPasswords: List<LocationPasswordEntity>): Response<None, Error> {
        return Response.Failure(Error.UnsupportedOperationError())
    }

}