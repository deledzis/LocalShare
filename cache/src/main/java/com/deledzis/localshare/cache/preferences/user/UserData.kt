package com.deledzis.localshare.cache.preferences.user

import com.deledzis.localshare.cache.mapper.UserMapper
import com.deledzis.localshare.domain.model.BaseUserData
import com.deledzis.localshare.domain.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserData @Inject constructor(
    private val userStore: UserStore,
    private val mapper: UserMapper
) : BaseUserData() {
    var user: AuthenticatedUser? = AuthenticatedUser.restore(userStore = userStore)
        set(value) {
            field = value
            if (value == null) {
                AuthenticatedUser.clear(userStore = userStore)
            } else {
                value.save(userStore = userStore)
            }
        }

    override fun getUser(): User? {
        return this.user?.let { mapper.mapFromEntity(it) }
    }

    override fun saveUser(user: User): Boolean {
        this.user = mapper.mapToEntity(user)
        return true
    }
}