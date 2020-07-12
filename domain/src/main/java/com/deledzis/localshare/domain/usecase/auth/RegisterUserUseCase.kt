package com.deledzis.localshare.domain.usecase.auth

import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.request.RegisterUserRequest
import com.deledzis.localshare.domain.repository.AuthRepository
import com.deledzis.localshare.domain.usecase.BaseUseCase
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<RegisterUserRequest>() {

    override suspend fun run(params: RegisterUserRequest) {
        // Started loading
        resultChannel.send(Response.State.Loading())

        // synchronous
//        delay(1000)
        val auth = authRepository.register(
            email = params.email,
            password = params.password
        )
        resultChannel.send(auth)

        resultChannel.send(Response.State.Loaded())
    }
}