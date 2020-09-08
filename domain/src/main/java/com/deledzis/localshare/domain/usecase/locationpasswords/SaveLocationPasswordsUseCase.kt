package com.deledzis.localshare.domain.usecase.locationpasswords

import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.LocationPassword
import com.deledzis.localshare.domain.repository.LocationPasswordsRepository
import com.deledzis.localshare.domain.usecase.BaseUseCase
import javax.inject.Inject

class SaveLocationPasswordsUseCase @Inject constructor(
    private val locationPasswordsRepository: LocationPasswordsRepository
) : BaseUseCase<List<LocationPassword>>() {

    override suspend fun run(params: List<LocationPassword>) {
        // Started loading
        resultChannel.send(Response.State.Loading())

        val response =
            locationPasswordsRepository.saveLocationPasswords(locationPasswords = params)

        resultChannel.send(response)

        // Done loading
        resultChannel.send(Response.State.Loaded())
    }
}