package com.deledzis.localshare.cache

import android.util.Log
import com.deledzis.localshare.cache.db.dao.TrackingPasswordsDao
import com.deledzis.localshare.cache.db.mapper.TrackingPasswordEntityMapper
import com.deledzis.localshare.cache.preferences.trackingpasswordscache.TrackingPasswordsLastCacheTime
import com.deledzis.localshare.data.model.TrackingPasswordEntity
import com.deledzis.localshare.data.repository.trackingpasswords.TrackingPasswordsCache
import javax.inject.Inject

/**
 * Cached implementation for retrieving and saving SearchResult instances. This class implements the
 * [TrackingPasswordEntity] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class TrackingPasswordsCacheImpl @Inject constructor(
    private val entityMapper: TrackingPasswordEntityMapper,
    private val trackingPasswordsLastCacheTime: TrackingPasswordsLastCacheTime,
    private val trackingPasswordsDao: TrackingPasswordsDao
) : TrackingPasswordsCache {

    private val EXPIRATION_TIME = (1000 * 60 * 60 * 24).toLong() // 24 hours

    /**
     * Retrieve a list of [TrackingPasswordEntity] instances from the database.
     */
    override suspend fun getTrackingPasswords(): List<TrackingPasswordEntity> {
        return trackingPasswordsDao.getTrackingPasswords().map {
            entityMapper.mapFromCached(it)
        }
    }

    override suspend fun updateTrackingPassword(trackingPasswordEntity: TrackingPasswordEntity): Int {
        return trackingPasswordsDao.updateTrackingPassword(
            trackingPassword = entityMapper.mapToCached(
                trackingPasswordEntity
            )
        )
    }

    override suspend fun deleteTrackingPassword(password: String): Int {
        return trackingPasswordsDao.deleteTrackingPassword(password = password)
    }

    /**
     * Remove all the data from all the tables in the database.
     */
    override suspend fun clearTrackingPasswords() {
        trackingPasswordsDao.deleteTrackingPasswords()
    }

    /**
     * Save the given list of [TrackingPasswordEntity] instances to the database.
     */
    override suspend fun saveTrackingPasswords(trackingPasswords: List<TrackingPasswordEntity>) {
        val inCache = trackingPasswordsDao.getTrackingPasswords()
        Log.e("TAG", "In Cache: $inCache")
        Log.e("TAG", "Inserting: $trackingPasswords")
        val result =
            trackingPasswordsDao.insertTrackingPasswords(trackingPasswords = trackingPasswords.map {
                entityMapper.mapToCached(it)
            })
        Log.e("TAG", "Inserted: $result")
    }

    override suspend fun saveTrackingPassword(trackingPasswordEntity: TrackingPasswordEntity) {
        trackingPasswordsDao.insertTrackingPassword(
            trackingPassword = entityMapper.mapToCached(
                trackingPasswordEntity
            )
        )
    }

    /**
     * Checked whether there are instances of [TrackingPasswordEntity] stored in the cache
     */
    override suspend fun isCached(password: String): Boolean {
        val cachedTrackingPasswords = trackingPasswordsDao.getTrackingPassword(
            password = password
        )
        return cachedTrackingPasswords != null
    }

    /**
     * Set a point in time at when the cache was last updated
     */
    override suspend fun setLastCacheTime(lastCache: Long) {
        trackingPasswordsLastCacheTime.lastCacheTime = lastCache
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
        return trackingPasswordsLastCacheTime.lastCacheTime ?: 0L
    }
}