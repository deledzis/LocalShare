package com.deledzis.localshare.cache.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.deledzis.localshare.cache.LocationPasswordsCacheImpl
import com.deledzis.localshare.cache.TrackingPasswordsCacheImpl
import com.deledzis.localshare.cache.db.Database
import com.deledzis.localshare.cache.db.dao.LocationPasswordsDao
import com.deledzis.localshare.cache.db.dao.TrackingPasswordsDao
import com.deledzis.localshare.cache.db.dao.UsersDao
import com.deledzis.localshare.cache.db.mapper.LocationPasswordEntityMapper
import com.deledzis.localshare.cache.db.mapper.TrackingPasswordEntityMapper
import com.deledzis.localshare.cache.mapper.UserMapper
import com.deledzis.localshare.cache.preferences.locationpasswordscache.LocationPasswordsLastCacheTime
import com.deledzis.localshare.cache.preferences.trackingpasswordscache.TrackingPasswordsLastCacheTime
import com.deledzis.localshare.cache.preferences.user.UserData
import com.deledzis.localshare.cache.preferences.user.UserStore
import com.deledzis.localshare.data.repository.locationpasswords.LocationPasswordsCache
import com.deledzis.localshare.data.repository.trackingpasswords.TrackingPasswordsCache
import com.deledzis.localshare.domain.model.BaseUserData
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataCacheModule {
    companion object {
        private const val PREF_LOCALSHARE_PACKAGE_NAME = "com.deledzis.localshare.preferences"

        @get:Synchronized
        var database: Database? = null
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            PREF_LOCALSHARE_PACKAGE_NAME,
            Context.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    fun provideLocationPasswordsLastCacheTime(sharedPreferences: SharedPreferences): LocationPasswordsLastCacheTime {
        return LocationPasswordsLastCacheTime(
            preferences = sharedPreferences
        )
    }

    @Singleton
    @Provides
    fun provideTrackingPasswordsLastCacheTime(sharedPreferences: SharedPreferences): TrackingPasswordsLastCacheTime {
        return TrackingPasswordsLastCacheTime(
            preferences = sharedPreferences
        )
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context): Database {
        synchronized(this) {
            if (database == null) {
                database = Room.databaseBuilder(
                    context,
                    Database::class.java,
                    "main_db"
                ).build()
            }
        }
        return database!!
    }

    @Singleton
    @Provides
    fun provideLocationPasswordsDao(database: Database): LocationPasswordsDao {
        return database.locationPasswordsDao()
    }

    @Singleton
    @Provides
    fun provideTrackingPasswordsDao(database: Database): TrackingPasswordsDao {
        return database.trackingPasswordsDao()
    }

    @Singleton
    @Provides
    fun provideUsersDao(database: Database): UsersDao {
        return database.usersDao()
    }

    @Singleton
    @Provides
    fun provideLocationPasswordsCache(
        entityMapper: LocationPasswordEntityMapper,
        locationPasswordsLastCacheTime: LocationPasswordsLastCacheTime,
        locationPasswordsDao: LocationPasswordsDao
    ): LocationPasswordsCache {
        return LocationPasswordsCacheImpl(
            entityMapper = entityMapper,
            locationPasswordsLastCacheTime = locationPasswordsLastCacheTime,
            locationPasswordsDao = locationPasswordsDao
        )
    }

    @Singleton
    @Provides
    fun provideTrackingPasswordsCache(
        entityMapper: TrackingPasswordEntityMapper,
        trackingPasswordsLastCacheTime: TrackingPasswordsLastCacheTime,
        trackingPasswordsDao: TrackingPasswordsDao
    ): TrackingPasswordsCache {
        return TrackingPasswordsCacheImpl(
            entityMapper = entityMapper,
            trackingPasswordsLastCacheTime = trackingPasswordsLastCacheTime,
            trackingPasswordsDao = trackingPasswordsDao
        )
    }

    @Singleton
    @Provides
    fun provideUserStore(
        userStore: UserStore,
        mapper: UserMapper
    ): BaseUserData {
        return UserData(
            userStore = userStore,
            mapper = mapper
        )
    }
}
