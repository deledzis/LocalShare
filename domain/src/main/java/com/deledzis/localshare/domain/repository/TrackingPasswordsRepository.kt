package com.deledzis.localshare.domain.repository

import com.deledzis.localshare.common.usecase.Error
import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.TrackingPassword
import com.deledzis.localshare.domain.model.response.trackingpasswords.*

/**
 * Interface defining methods for how the Data layer can pass data to and from the Domain layer.
 * This is to be implemented by the Data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface TrackingPasswordsRepository {

    suspend fun getTrackingPasswords(
        userId: Int,
        refresh: Boolean
    ): Response<GetTrackingPasswordsResponse, Error>

    suspend fun addTrackingPassword(
        password: String,
        description: String
    ): Response<AddTrackingPasswordResponse, Error>

    suspend fun updateTrackingPassword(
        trackingPassword: TrackingPassword
    ): Response<UpdateTrackingPasswordResponse, Error>

    suspend fun deleteTrackingPassword(
        password: String
    ): Response<DeleteTrackingPasswordResponse, Error>

    suspend fun clearTrackingPasswords(): Response<ClearTrackingPasswordsResponse, Error>

    suspend fun saveTrackingPasswords(
        trackingPasswords: List<TrackingPassword>
    ): Response<SaveTrackingPasswordsResponse, Error>

}