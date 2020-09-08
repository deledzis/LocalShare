package com.deledzis.localshare.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deledzis.localshare.cache.db.dao.LocationPasswordsDao
import com.deledzis.localshare.cache.db.dao.TrackingPasswordsDao
import com.deledzis.localshare.cache.db.dao.UsersDao
import com.deledzis.localshare.cache.db.model.CachedLocationPassword
import com.deledzis.localshare.cache.db.model.CachedTrackingPassword
import com.deledzis.localshare.cache.db.model.CachedUser

@Database(
    entities = [
        CachedLocationPassword::class,
        CachedTrackingPassword::class,
        CachedUser::class
    ],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract fun locationPasswordsDao(): LocationPasswordsDao
    abstract fun trackingPasswordsDao(): TrackingPasswordsDao
    abstract fun usersDao(): UsersDao
}