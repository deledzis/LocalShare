package com.deledzis.localshare.data.model.fcm_token

import android.content.SharedPreferences
import com.deledzis.localshare.data.model.auth.Auth
import com.deledzis.localshare.data.model.fcm_token.FcmToken
import com.deledzis.localshare.di.scopes.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class FcmTokenData @Inject constructor(private val preferences: SharedPreferences) {
    var fcmToken: FcmToken? = FcmToken.restore(preferences)
        set(value) {
            field = value
            if (value == null) {
                Auth.clear(preferences)
            } else {
                value.save(preferences)
            }
        }
}