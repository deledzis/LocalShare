package com.deledzis.localshare.domain.usecase.locationpassword

import com.deledzis.localshare.domain.model.LocationPassword
import com.deledzis.localshare.domain.repository.LocationPasswordsRepository
import com.deledzis.localshare.domain.usecase.BaseUseCase
import javax.inject.Inject

class UpdateLocationPasswordUseCase @Inject constructor(
    private val locationPasswordsRepository: LocationPasswordsRepository
) : BaseUseCase<LocationPassword>() {

    override suspend fun run(params: LocationPassword) {
//        delay(1000)
        val response =
            locationPasswordsRepository.updateLocationPassword(locationPassword = params)

        resultChannel.send(response)
    }
}