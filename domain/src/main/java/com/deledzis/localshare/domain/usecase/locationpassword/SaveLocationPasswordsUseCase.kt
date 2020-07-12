package com.deledzis.localshare.domain.usecase.locationpassword

import com.deledzis.localshare.domain.model.LocationPassword
import com.deledzis.localshare.domain.repository.LocationPasswordsRepository
import com.deledzis.localshare.domain.usecase.BaseUseCase
import javax.inject.Inject

class SaveLocationPasswordsUseCase @Inject constructor(
    private val locationPasswordsRepository: LocationPasswordsRepository
) : BaseUseCase<List<LocationPassword>>() {

    override suspend fun run(params: List<LocationPassword>) {
//        delay(1000)
        val response =
            locationPasswordsRepository.saveLocationPasswords(locationPasswords = params)

        resultChannel.send(response)
    }
}