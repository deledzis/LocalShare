package com.deledzis.localshare.data.source.trackingpasswords

import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.data.model.TrackingPasswordEntity
import com.deledzis.localshare.data.repository.trackingpasswords.TrackingPasswordsDataStore
import com.deledzis.localshare.data.repository.trackingpasswords.TrackingPasswordsRemote
import com.deledzis.localshare.domain.usecase.None
import javax.inject.Inject

/**
 * Implementation of the [TrackingPasswordsDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class TrackingPasswordsRemoteDataStore @Inject constructor(
    private val trackingPasswordsRemote: TrackingPasswordsRemote
) : TrackingPasswordsDataStore {

    override suspend fun getTrackingPasswords(userId: Int): Response<List<TrackingPasswordEntity>, Error> {
        return try {
            val locationPasswordsFromRemote =
                trackingPasswordsRemote.getTrackingPasswords(userId = userId)
            Response.Success(successData = locationPasswordsFromRemote)
        } catch (e: Exception) {
            Response.Failure(Error.NetworkError(exception = e))
        }
    }

    override suspend fun addTrackingPassword(
        password: String,
        description: String
    ): Response<Boolean, Error> {
        return try {
            val added = trackingPasswordsRemote.addTrackingPassword(
                password = password,
                description = description
            )
            Response.Success(successData = added)
        } catch (e: Exception) {
            Response.Failure(Error.NetworkError(exception = e))
        }
    }

    override suspend fun updateTrackingPassword(trackingPassword: TrackingPasswordEntity): Response<Boolean, Error> {
        return try {
            val updated = trackingPasswordsRemote.updateTrackingPassword(
                trackingPasswordEntity = trackingPassword
            )
            Response.Success(successData = updated)
        } catch (e: Exception) {
            Response.Failure(Error.NetworkError(exception = e))
        }
    }

    override suspend fun deleteTrackingPassword(password: String): Response<Boolean, Error> {
        return try {
            val deleted = trackingPasswordsRemote.deleteTrackingPassword(password = password)
            Response.Success(successData = deleted)
        } catch (e: Exception) {
            Response.Failure(Error.NetworkError(exception = e))
        }
    }

    override suspend fun clearTrackingPasswords(): Response<None, Error> {
        return Response.Failure(Error.UnsupportedOperationError())
    }

    override suspend fun saveTrackingPasswords(trackingPasswords: List<TrackingPasswordEntity>): Response<None, Error> {
        return Response.Failure(Error.UnsupportedOperationError())
    }

}