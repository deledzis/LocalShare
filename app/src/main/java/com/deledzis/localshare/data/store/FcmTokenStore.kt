package com.deledzis.localshare.data.store

import android.content.SharedPreferences

class FcmTokenStore(preferences: SharedPreferences) {

    var fcmToken: String? by PreferencesDelegate(preferences, FCM_TOKEN_DATA, "")

    companion object {
        private const val FCM_TOKEN_DATA = "fcm_token_data"
    }
}