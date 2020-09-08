package com.deledzis.localshare.domain.usecase.locationpasswords

import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.repository.LocationPasswordsRepository
import com.deledzis.localshare.domain.usecase.BaseUseCase
import javax.inject.Inject

class DeleteLocationPasswordUseCase @Inject constructor(
    private val locationPasswordsRepository: LocationPasswordsRepository
) : BaseUseCase<String>() {

    override suspend fun run(params: String) {
        // Started loading
        resultChannel.send(Response.State.Loading())

        val response =
            locationPasswordsRepository.deleteLocationPassword(password = params)

        resultChannel.send(response)

        // Done loading
        resultChannel.send(Response.State.Loaded())
    }
}