package com.deledzis.localshare.domain.usecase.auth

import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.request.AuthUserRequest
import com.deledzis.localshare.domain.repository.AuthRepository
import com.deledzis.localshare.domain.usecase.BaseUseCase
import javax.inject.Inject

class AuthUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<AuthUserRequest>() {

    override suspend fun run(params: AuthUserRequest) {
        // Started loading
        resultChannel.send(Response.State.Loading())

        // synchronous
//        delay(1000)
        val auth = authRepository.auth(
            email = params.email,
            password = params.password
        )
        resultChannel.send(auth)

        resultChannel.send(Response.State.Loaded())
    }
}