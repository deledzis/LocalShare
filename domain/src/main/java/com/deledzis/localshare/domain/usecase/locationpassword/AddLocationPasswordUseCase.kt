package com.deledzis.localshare.domain.usecase.locationpassword

import com.deledzis.localshare.domain.model.request.AddLocationPasswordRequest
import com.deledzis.localshare.domain.repository.LocationPasswordsRepository
import com.deledzis.localshare.domain.usecase.BaseUseCase
import javax.inject.Inject

class AddLocationPasswordUseCase @Inject constructor(
    private val locationPasswordsRepository: LocationPasswordsRepository
) : BaseUseCase<AddLocationPasswordRequest>() {

    override suspend fun run(params: AddLocationPasswordRequest) {
        // Update password remotely and in cache
//        delay(1000)
        val response =
            locationPasswordsRepository.addLocationPassword(
                password = params.password,
                description = params.description
            )

        resultChannel.send(response)
    }
}