package com.deledzis.localshare.domain.usecase.trackingpasswords

import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.request.trackingpasswords.GetTrackingPasswordsRequest
import com.deledzis.localshare.domain.repository.TrackingPasswordsRepository
import com.deledzis.localshare.domain.usecase.BaseUseCase
import javax.inject.Inject

class GetTrackingPasswordsUseCase @Inject constructor(
    private val trackingPasswordsRepository: TrackingPasswordsRepository
) : BaseUseCase<GetTrackingPasswordsRequest>() {

    override suspend fun run(params: GetTrackingPasswordsRequest) {
        // Started loading
        resultChannel.send(Response.State.Loading())

        // Get passwords and send it, synchronous
        val passwords =
            trackingPasswordsRepository.getTrackingPasswords(
                userId = params.userId,
                refresh = params.refresh
            )
        resultChannel.send(passwords)

        // Done loading
        resultChannel.send(Response.State.Loaded())
    }
}