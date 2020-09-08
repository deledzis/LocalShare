package com.deledzis.localshare.domain.usecase.locationpasswords

import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.request.locationpasswords.AddLocationPasswordRequest
import com.deledzis.localshare.domain.repository.LocationPasswordsRepository
import com.deledzis.localshare.domain.usecase.BaseUseCase
import javax.inject.Inject

class AddLocationPasswordUseCase @Inject constructor(
    private val locationPasswordsRepository: LocationPasswordsRepository
) : BaseUseCase<AddLocationPasswordRequest>() {

    override suspend fun run(params: AddLocationPasswordRequest) {
        // Started loading
        resultChannel.send(Response.State.Loading())

        val response =
            locationPasswordsRepository.addLocationPassword(
                password = params.password,
                description = params.description
            )

        resultChannel.send(response)

        // Done loading
        resultChannel.send(Response.State.Loaded())
    }
}