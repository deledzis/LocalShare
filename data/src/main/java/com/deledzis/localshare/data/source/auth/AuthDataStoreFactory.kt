package com.deledzis.localshare.data.source.auth

import com.deledzis.localshare.data.repository.auth.AuthDataStore
import javax.inject.Inject

/**
 * Create an instance of a SearchResultsDataStore
 */
open class AuthDataStoreFactory @Inject constructor(
    private val authRemoteDataStore: AuthRemoteDataStore
) {

    /**
     * Return an instance of the Cache Data Store
     */
    open fun retrieveRemoteDataStore(): AuthDataStore {
        return authRemoteDataStore
    }

}