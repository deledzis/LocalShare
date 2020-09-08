package com.deledzis.localshare.domain.usecase.auth

import com.deledzis.localshare.common.usecase.Response
import com.deledzis.localshare.domain.model.request.auth.ForgetPasswordRequest
import com.deledzis.localshare.domain.repository.AuthRepository
import com.deledzis.localshare.domain.usecase.BaseUseCase
import javax.inject.Inject

class ForgetPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<ForgetPasswordRequest>() {

    override suspend fun run(params: ForgetPasswordRequest) {
        // Started loading
        resultChannel.send(Response.State.Loading())

        // synchronous
//        delay(1000)
        val auth = authRepository.forgetPassword(
            email = params.email
        )
        resultChannel.send(auth)

        resultChannel.send(Response.State.Loaded())
    }
}