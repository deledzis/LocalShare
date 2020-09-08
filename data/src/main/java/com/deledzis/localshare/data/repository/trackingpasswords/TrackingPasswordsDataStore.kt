package com.deledzis.localshare.data.repository.trackingpasswords

import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.data.model.TrackingPasswordEntity
import com.deledzis.localshare.domain.usecase.None

/**
 * Interface defining methods for the data operations related to [TrackingPasswordEntity].
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface TrackingPasswordsDataStore {

    suspend fun getTrackingPasswords(userId: Int): Response<List<TrackingPasswordEntity>, Error>

    suspend fun addTrackingPassword(password: String, description: String): Response<Boolean, Error>

    suspend fun updateTrackingPassword(trackingPassword: TrackingPasswordEntity): Response<Boolean, Error>

    suspend fun deleteTrackingPassword(password: String): Response<Boolean, Error>

    suspend fun clearTrackingPasswords(): Response<None, Error>

    suspend fun saveTrackingPasswords(trackingPasswords: List<TrackingPasswordEntity>): Response<None, Error>

}