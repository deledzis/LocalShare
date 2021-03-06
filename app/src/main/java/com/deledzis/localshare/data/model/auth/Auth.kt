package com.deledzis.localshare.data.model.auth

import android.content.SharedPreferences
import com.deledzis.localshare.data.store.UserStore
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Auth(
    val token: String?,
    @SerializedName("user_id")
    val userId: Int
) {
    fun save(preferences: SharedPreferences) {
        val store = UserStore(preferences)
        store.userData = Gson().toJson(this, Auth::class.java)
    }

    companion object {
        fun restore(preferences: SharedPreferences): Auth? {
            val store = UserStore(preferences)
            return Gson().fromJson<Auth>(store.userData, Auth::class.java)
        }

        fun clear(preferences: SharedPreferences) {
            UserStore(preferences).userData = null
        }
    }
}