package com.deledzis.localshare.data.source.trackingpasswords

import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.data.model.LastCoordinatesEntity
import com.deledzis.localshare.data.model.TrackingPasswordEntity
import com.deledzis.localshare.data.repository.trackingpasswords.TrackingPasswordsCache
import com.deledzis.localshare.data.repository.trackingpasswords.TrackingPasswordsDataStore
import com.deledzis.localshare.domain.usecase.None
import java.util.*
import javax.inject.Inject

/**
 * Implementation of the [TrackingPasswordsDataStore] interface to provide a means of communicating
 * with the local data source
 */
open class TrackingPasswordsCacheDataStore @Inject constructor(
    private val trackingPasswordsCache: TrackingPasswordsCache
) : TrackingPasswordsDataStore {

    /**
     * Retrieve a list of [TrackingPasswordEntity] instance from the cache
     */
    override suspend fun getTrackingPasswords(userId: Int): Response<List<TrackingPasswordEntity>, Error> {
        return try {
            val trackingPasswordsFromCache =
                trackingPasswordsCache.getTrackingPasswords()
            Response.Success(successData = trackingPasswordsFromCache)
        } catch (e: Exception) {
            Response.Failure(Error.PersistenceError(exception = e))
        }
    }

    override suspend fun addTrackingPassword(
        password: String,
        description: String
    ): Response<Boolean, Error> {
        return try {
            // TODO: remove later
            trackingPasswordsCache.saveTrackingPassword(trackingPasswordEntity = TrackingPasswordEntity(
                id = (1L..9_999_999_999L).random(),
                password = password,
                description = description,
                active = false,
                lastCoordinates = LastCoordinatesEntity(
                    lat = 57.9698694,
                    lng = 31.3442704
                ),
                lastUpdateTime = Calendar.getInstance().timeInMillis
            ))
            trackingPasswordsCache.setLastCacheTime(System.currentTimeMillis())
            Response.Success(successData = true)
        } catch (e: Exception) {
            Response.Failure(Error.PersistenceError(exception = e))
        }
    }

    override suspend fun updateTrackingPassword(trackingPassword: TrackingPasswordEntity): Response<Boolean, Error> {
        return try {
            val updatedRows = trackingPasswordsCache.updateTrackingPassword(
                trackingPasswordEntity = trackingPassword
            )
            println("HERE: UPDATED $updatedRows")
            Response.Success(successData = updatedRows > 0)
        } catch (e: Exception) {
            Response.Failure(Error.PersistenceError(exception = e))
        }
    }

    override suspend fun deleteTrackingPassword(password: String): Response<Boolean, Error> {
        return try {
            val deletedRows = trackingPasswordsCache.deleteTrackingPassword(password = password)
            Response.Success(successData = deletedRows > 0)
        } catch (e: Exception) {
            Response.Failure(Error.PersistenceError(exception = e))
        }
    }

    /**
     * Clear all [TrackingPasswordEntity] from the cache
     */
    override suspend fun clearTrackingPasswords(): Response<None, Error> {
        return try {
            trackingPasswordsCache.clearTrackingPasswords()
            Response.Success(None())
        } catch (e: Exception) {
            Response.Failure(Error.PersistenceError(exception = e))
        }
    }

    /**
     * Save a given [List] of [TrackingPasswordEntity] instances to the cache
     */
    override suspend fun saveTrackingPasswords(trackingPasswords: List<TrackingPasswordEntity>): Response<None, Error> {
        return try {
            trackingPasswordsCache.saveTrackingPasswords(trackingPasswords)
            trackingPasswordsCache.setLastCacheTime(System.currentTimeMillis())
            Response.Success(None())
        } catch (e: Exception) {
            Response.Failure(Error.PersistenceError(exception = e))
        }
    }

}