package com.deledzis.localshare.cache

import com.deledzis.localshare.cache.db.dao.LocationPasswordsDao
import com.deledzis.localshare.cache.db.mapper.LocationPasswordEntityMapper
import com.deledzis.localshare.cache.preferences.locationpasswordscache.LocationPasswordsLastCacheTime
import com.deledzis.localshare.data.source.datastore.locationpassword.LocationPasswordsCache
import com.deledzis.localshare.data.source.server.model.LocationPasswordEntity
import javax.inject.Inject

/**
 * Cached implementation for retrieving and saving SearchResult instances. This class implements the
 * [LocationPasswordEntity] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class LocationPasswordsCacheImpl @Inject constructor(
    private val entityMapper: LocationPasswordEntityMapper,
    private val locationPasswordsLastCacheTime: LocationPasswordsLastCacheTime,
    private val locationPasswordsDao: LocationPasswordsDao
) : LocationPasswordsCache {

    private val EXPIRATION_TIME = (60 * 10 * 1000).toLong() // 10 minutes

    /**
     * Retrieve a list of [LocationPasswordEntity] instances from the database.
     */
    override suspend fun getLocationPasswords(userId: Int): List<LocationPasswordEntity> {
        return locationPasswordsDao.getLocationPasswordsByUserId(userId = userId).map {
            entityMapper.mapFromCached(it)
        }
    }

    override suspend fun updateLocationPassword(locationPasswordEntity: LocationPasswordEntity): Int {
        return locationPasswordsDao.updateLocationPassword(
            locationPassword = entityMapper.mapToCached(
                locationPasswordEntity
            )
        )
    }

    override suspend fun deleteLocationPassword(id: Int): Int {
        return locationPasswordsDao.deleteLocationPassword(id = id)
    }

    /**
     * Remove all the data from all the tables in the database.
     */
    override suspend fun clearLocationPasswords() {
        locationPasswordsDao.deleteLocationPasswords()
    }

    /**
     * Save the given list of [LocationPasswordEntity] instances to the database.
     */
    override suspend fun saveLocationPasswords(locationPasswords: List<LocationPasswordEntity>) {
        locationPasswordsDao.insertLocationPasswords(locationPasswords = locationPasswords.map {
            entityMapper.mapToCached(
                it
            )
        })
    }

    /**
     * Checked whether there are instances of [LocationPasswordEntity] stored in the cache
     */
    override suspend fun isCached(id: Int): Boolean {
        val cachedLocationPassword = locationPasswordsDao.getLocationPassword(
            id = id
        )
        return cachedLocationPassword != null
    }

    /**
     * Set a point in time at when the cache was last updated
     */
    override suspend fun setLastCacheTime(lastCache: Long) {
        locationPasswordsLastCacheTime.lastCacheTime = lastCache
    }

    /**
     * Check whether the current cached data exceeds the defined [EXPIRATION_TIME] time
     */
    override suspend fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return locationPasswordsLastCacheTime.lastCacheTime ?: 0L
    }
}