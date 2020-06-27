package com.deledzis.localshare.data.model.fcm_token

import android.content.SharedPreferences
import com.deledzis.localshare.data.store.FcmTokenStore
import com.google.gson.Gson

data class FcmToken(
    val token: String
) {
    fun save(preferences: SharedPreferences) {
        val store = FcmTokenStore(preferences)
        store.fcmToken = Gson().toJson(this, FcmToken::class.java)
    }

    companion object {
        fun restore(preferences: SharedPreferences): FcmToken? {
            val store = FcmTokenStore(preferences)
            return Gson().fromJson<FcmToken>(store.fcmToken, FcmToken::class.java)
        }

        fun clear(preferences: SharedPreferences) {
            FcmTokenStore(preferences).fcmToken = null
        }
    }
}