package com.deledzis.localshare.cache.preferences.trackingpasswordscache

import android.content.SharedPreferences
import com.deledzis.localshare.cache.preferences.PreferencesDelegate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackingPasswordsLastCacheTime @Inject constructor(preferences: SharedPreferences) {

    var lastCacheTime: Long? by PreferencesDelegate(
        preferences,
        PREF_KEY_PASSWORDS_LAST_CACHE_TIME,
        0L
    )

    companion object {
        private const val PREF_KEY_PASSWORDS_LAST_CACHE_TIME = "tracking_passwords_last_cache_time"
    }
}