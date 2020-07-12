package com.deledzis.localshare.domain.usecase.locationpassword

import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.request.GetLocationPasswordsRequest
import com.deledzis.localshare.domain.repository.LocationPasswordsRepository
import com.deledzis.localshare.domain.usecase.BaseUseCase
import javax.inject.Inject

class GetLocationPasswordsUseCase @Inject constructor(
    private val locationPasswordsRepository: LocationPasswordsRepository
) : BaseUseCase<GetLocationPasswordsRequest>() {

    override suspend fun run(params: GetLocationPasswordsRequest) {
        // Started loading
        resultChannel.send(Response.State.Loading())

        // Get passwords and send it, synchronous
        val passwords =
            locationPasswordsRepository.getLocationPasswords(
                userId = params.userId,
                refresh = params.refresh
            )
        resultChannel.send(passwords)

        resultChannel.send(Response.State.Loaded())
    }
}