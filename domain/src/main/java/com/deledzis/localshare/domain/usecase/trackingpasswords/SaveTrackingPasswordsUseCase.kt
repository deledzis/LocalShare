package com.deledzis.localshare.domain.usecase.trackingpasswords

import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.TrackingPassword
import com.deledzis.localshare.domain.repository.TrackingPasswordsRepository
import com.deledzis.localshare.domain.usecase.BaseUseCase
import javax.inject.Inject

class SaveTrackingPasswordsUseCase @Inject constructor(
    private val trackingPasswordsRepository: TrackingPasswordsRepository
) : BaseUseCase<List<TrackingPassword>>() {

    override suspend fun run(params: List<TrackingPassword>) {
        // Started loading
        resultChannel.send(Response.State.Loading())

        val response =
            trackingPasswordsRepository.saveTrackingPasswords(trackingPasswords = params)

        resultChannel.send(response)

        // Done loading
        resultChannel.send(Response.State.Loaded())
    }
}