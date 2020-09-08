package com.deledzis.localshare.domain.model

import javax.inject.Singleton

@Singleton
abstract class BaseUserData {
    abstract fun getUser(): User?

    abstract fun saveUser(user: User?): Boolean
}