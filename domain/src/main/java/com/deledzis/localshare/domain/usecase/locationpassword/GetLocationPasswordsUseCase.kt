package com.deledzis.localshare.domain.usecase.locationpassword

import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.repository.LocationPasswordsRepository
import com.deledzis.localshare.domain.usecase.BaseUseCase
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetLocationPasswordsUseCase @Inject constructor(
    private val locationPasswordsRepository: LocationPasswordsRepository
) : BaseUseCase<Int>() {

    override suspend fun run(params: Int) {
        // Started loading
        resultChannel.send(Response.State.Loading())

        // Get passwords and send it, synchronous
        delay(1000)
        val passwords = locationPasswordsRepository.getLocationPasswords(userId = params)
        resultChannel.send(passwords)

        resultChannel.send(Response.State.Loaded())
        passwords.handleResult {
            // Save passwords asynchronously
            val deferred = startAsync {
                delay(500)
                locationPasswordsRepository.saveLocationPasswords(it)
            }
        }
    }
}