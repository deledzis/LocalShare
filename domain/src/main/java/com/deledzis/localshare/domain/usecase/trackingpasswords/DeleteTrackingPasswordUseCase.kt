package com.deledzis.localshare.domain.usecase.trackingpasswords

import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.repository.TrackingPasswordsRepository
import com.deledzis.localshare.domain.usecase.BaseUseCase
import javax.inject.Inject

class DeleteTrackingPasswordUseCase @Inject constructor(
    private val trackingPasswordsRepository: TrackingPasswordsRepository
) : BaseUseCase<String>() {

    override suspend fun run(params: String) {
        // Started loading
        resultChannel.send(Response.State.Loading())

        val response =
            trackingPasswordsRepository.deleteTrackingPassword(password = params)

        resultChannel.send(response)

        // Done loading
        resultChannel.send(Response.State.Loaded())
    }
}