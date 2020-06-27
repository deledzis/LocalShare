package com.deledzis.localshare.di.model

import android.content.SharedPreferences
import com.deledzis.localshare.data.model.auth.Auth
import com.deledzis.localshare.data.model.user.User
import com.deledzis.localshare.di.scopes.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class UserData @Inject constructor(private val preferences: SharedPreferences) {
    var auth: Auth? = Auth.restore(preferences)
        set(value) {
            field = value
            if (value == null) {
                Auth.clear(preferences)
            } else {
                value.save(preferences)
            }
        }

    var user: User? = null
}