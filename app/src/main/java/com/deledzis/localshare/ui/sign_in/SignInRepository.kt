package com.deledzis.localshare.ui.sign_in

import com.deledzis.localshare.api.ApiInterface
import com.deledzis.localshare.base.BaseRepository
import com.deledzis.localshare.data.model.auth.Auth
import com.deledzis.localshare.data.model.auth.AuthUserRequest
import com.deledzis.localshare.data.model.user.User

class SignInRepository(private val api: ApiInterface) : BaseRepository() {
    suspend fun login(email: String?, password: String?): Auth? {
        return safeApiCall(
            call = {
                api.authUser(
                    AuthUserRequest(
                        email = email ?: "",
                        password = password ?: ""
                    )
                )
            },
            errorMessage = "Error while login"
        )
    }

    suspend fun userDetails(id: Int): User? {
        return null
        // TODO
        /*return safeApiCall(
            call = { App.injector.api().getEmployee(id) },
            errorMessage = "Error Fetching User Data"
        )*/
    }
}