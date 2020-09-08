package com.deledzis.localshare.domain.usecase.trackingpasswords

import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.request.trackingpasswords.AddTrackingPasswordRequest
import com.deledzis.localshare.domain.repository.TrackingPasswordsRepository
import com.deledzis.localshare.domain.usecase.BaseUseCase
import javax.inject.Inject

class AddTrackingPasswordUseCase @Inject constructor(
    private val trackingPasswordsRepository: TrackingPasswordsRepository
) : BaseUseCase<AddTrackingPasswordRequest>() {

    override suspend fun run(params: AddTrackingPasswordRequest) {
        // Started loading
        resultChannel.send(Response.State.Loading())

        val response =
            trackingPasswordsRepository.addTrackingPassword(
                password = params.password,
                description = params.description
            )

        resultChannel.send(response)

        // Done loading
        resultChannel.send(Response.State.Loaded())
    }
}