package com.deledzis.localshare.domain.model

abstract class BaseUserData {
    abstract fun getUser(): User?

    abstract fun saveUser(user: User): Boolean
}