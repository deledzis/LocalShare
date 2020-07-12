package com.deledzis.localshare.data.source.locationpassword

import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.data.model.LocationPasswordEntity
import com.deledzis.localshare.data.repository.locationpassword.LocationPasswordsCache
import com.deledzis.localshare.data.repository.locationpassword.LocationPasswordsDataStore
import com.deledzis.localshare.domain.usecase.None
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
    override suspend fun getLocationPasswords(userId: Int): Response<List<LocationPasswordEntity>, Error> {
        return try {
            val locationPasswordsFromCache =
                locationPasswordsCache.getLocationPasswords(userId = userId)
            Response.Success(successData = locationPasswordsFromCache)
        } catch (e: Exception) {
            Response.Failure(Error.PersistenceError(exception = e))
        }
    }

    override suspend fun addLocationPassword(
        password: String,
        description: String
    ): Response<Boolean, Error> {
        return Response.Failure(Error.UnsupportedOperationError())
    }

    override suspend fun updateLocationPassword(locationPassword: LocationPasswordEntity): Response<Boolean, Error> {
        return try {
            val updatedRows = locationPasswordsCache.updateLocationPassword(
                locationPasswordEntity = locationPassword
            )
            Response.Success(successData = updatedRows > 0)
        } catch (e: Exception) {
            Response.Failure(Error.PersistenceError(exception = e))
        }
    }

    override suspend fun deleteLocationPassword(id: Int): Response<Boolean, Error> {
        return try {
            val deletedRows = locationPasswordsCache.deleteLocationPassword(id = id)
            Response.Success(successData = deletedRows > 0)
        } catch (e: Exception) {
            Response.Failure(Error.PersistenceError(exception = e))
        }
    }

    /**
     * Clear all [LocationPasswordEntity] from the cache
     */
    override suspend fun clearLocationPasswords(): Response<None, Error> {
        return try {
            locationPasswordsCache.clearLocationPasswords()
            Response.Success(None())
        } catch (e: Exception) {
            Response.Failure(Error.PersistenceError(exception = e))
        }
    }

    /**
     * Save a given [List] of [LocationPasswordEntity] instances to the cache
     */
    override suspend fun saveLocationPasswords(locationPasswords: List<LocationPasswordEntity>): Response<None, Error> {
        return try {
            locationPasswordsCache.saveLocationPasswords(locationPasswords)
            locationPasswordsCache.setLastCacheTime(System.currentTimeMillis())
            Response.Success(None())
        } catch (e: Exception) {
            Response.Failure(Error.PersistenceError(exception = e))
        }
    }

}