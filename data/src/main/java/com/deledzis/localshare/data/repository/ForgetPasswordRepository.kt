package com.deledzis.localshare.data.repository

import com.deledzis.localshare.common.BaseNetworkManager
import com.deledzis.localshare.data.source.server.ApiRemote
import com.deledzis.localshare.domain.repository.IForgetPasswordRepository
import javax.inject.Inject

class ForgetPasswordRepository @Inject constructor(
    apiRemote: ApiRemote,
    networkManager: BaseNetworkManager
) : BaseRepository(apiRemote = apiRemote, networkManager = networkManager),
    IForgetPasswordRepository {

    override suspend fun forgetPassword(email: String): Boolean {
        return safeApiCall(
            call = { apiRemote.recoverPassword(email = email) },
            errorMessage = "Error when try to recover password"
        ) ?: return false
    }
}