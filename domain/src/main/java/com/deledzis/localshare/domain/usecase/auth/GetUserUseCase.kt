package com.deledzis.localshare.domain.usecase.auth

import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.repository.AuthRepository
import com.deledzis.localshare.domain.usecase.BaseUseCase
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<Int>() {

    override suspend fun run(params: Int) {
        // Started loading
        resultChannel.send(Response.State.Loading())

        // Get passwords and send it, synchronous
        delay(1000)
        val user = authRepository.getUser(id = params)
        resultChannel.send(user)
        resultChannel.send(Response.State.Loaded())
    }
}