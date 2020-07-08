package com.deledzis.localshare.cache.preferences.user

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserData @Inject constructor(
    private val userStore: UserStore
) {
    var user: AuthenticatedUser? = AuthenticatedUser.restore(userStore = userStore)
        set(value) {
            field = value
            if (value == null) {
                AuthenticatedUser.clear(userStore = userStore)
            } else {
                value.save(userStore = userStore)
            }
        }
}