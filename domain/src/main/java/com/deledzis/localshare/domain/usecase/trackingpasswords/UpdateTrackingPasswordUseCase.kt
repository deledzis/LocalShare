package com.deledzis.localshare.domain.usecase.trackingpasswords

import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.TrackingPassword
import com.deledzis.localshare.domain.repository.TrackingPasswordsRepository
import com.deledzis.localshare.domain.usecase.BaseUseCase
import javax.inject.Inject

class UpdateTrackingPasswordUseCase @Inject constructor(
    private val trackingPasswordsRepository: TrackingPasswordsRepository
) : BaseUseCase<TrackingPassword>() {

    override suspend fun run(params: TrackingPassword) {
        println("UPD: HERE: 1")
        // Started loading
        resultChannel.send(Response.State.Loading())

        println("UPD: HERE: 2")
        val response =
            trackingPasswordsRepository.updateTrackingPassword(trackingPassword = params)

        println("UPD: HERE: 3")
        resultChannel.send(response)

        // Done loading
        resultChannel.send(Response.State.Loaded())
    }
}