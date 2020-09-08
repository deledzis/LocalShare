package com.deledzis.localshare.data.source.trackingpasswords

import com.deledzis.localshare.common.BaseNetworkManager
import com.deledzis.localshare.data.repository.trackingpasswords.TrackingPasswordsCache
import com.deledzis.localshare.data.repository.trackingpasswords.TrackingPasswordsDataStore
import javax.inject.Inject

/**
 * Create an instance of a SearchResultsDataStore
 */
open class TrackingPasswordsDataStoreFactory @Inject constructor(
    private val trackingPasswordsCache: TrackingPasswordsCache,
    private val trackingPasswordsCacheDataStore: TrackingPasswordsCacheDataStore,
    private val trackingPasswordsRemoteDataStore: TrackingPasswordsRemoteDataStore,
    private val networkManager: BaseNetworkManager
) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open suspend fun retrieveDataStore(password: String): TrackingPasswordsDataStore {
        val isCached = trackingPasswordsCache.isCached(password = password)
        if (isCached && !trackingPasswordsCache.isExpired()) {
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
    open fun retrieveCacheDataStore(): TrackingPasswordsDataStore {
        return trackingPasswordsCacheDataStore
    }

    /**
     * Return an instance of the Cache Data Store
     */
    open fun retrieveRemoteDataStore(): TrackingPasswordsDataStore {
        return trackingPasswordsRemoteDataStore
    }

}