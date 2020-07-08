package com.deledzis.localshare.cache.preferences.user

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class AuthenticatedUser(
    @SerializedName("id")
    val id: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("token")
    val token: String
) {
    fun save(userStore: UserStore) {
        userStore.userData = Gson().toJson(this, AuthenticatedUser::class.java)
    }

    companion object {
        fun restore(userStore: UserStore): AuthenticatedUser? {
            return Gson().fromJson<AuthenticatedUser>(userStore.userData, AuthenticatedUser::class.java)
        }

        fun clear(userStore: UserStore) {
            userStore.userData = null
        }
    }
}