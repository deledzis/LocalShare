package com.deledzis.localshare.data.source.locationpassword

import com.deledzis.localshare.common.BaseNetworkManager
import com.deledzis.localshare.data.repository.locationpassword.LocationPasswordsCache
import com.deledzis.localshare.data.repository.locationpassword.LocationPasswordsDataStore
import javax.inject.Inject

/**
 * Create an instance of a SearchResultsDataStore
 */
open class LocationPasswordsDataStoreFactory @Inject constructor(
    private val locationPasswordsCache: LocationPasswordsCache,
    private val locationPasswordsCacheDataStore: LocationPasswordsCacheDataStore,
    private val locationPasswordsRemoteDataStore: LocationPasswordsRemoteDataStore,
    private val networkManager: BaseNetworkManager
) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open suspend fun retrieveDataStore(userId: Int): LocationPasswordsDataStore {
        val isCached = locationPasswordsCache.isCached(userId = userId)
        if (isCached && !locationPasswordsCache.isExpired()) {
            return retrieveCacheDataStore()
        }
        if (!networkManager.isConnectedToInternet()) {
            return retrieveCacheDataStore()
        }

        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveCacheDataStore(): LocationPasswordsDataStore {
        return locationPasswordsCacheDataStore
    }

    /**
     * Return an instance of the Cache Data Store
     */
    open fun retrieveRemoteDataStore(): LocationPasswordsDataStore {
        return locationPasswordsRemoteDataStore
    }

}