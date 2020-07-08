package com.deledzis.localshare.domain.repository

interface IForgetPasswordRepository {
    suspend fun forgetPassword(email: String): Boolean
}