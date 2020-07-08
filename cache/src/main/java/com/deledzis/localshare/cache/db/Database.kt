package com.deledzis.localshare.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deledzis.localshare.cache.db.dao.LocationPasswordsDao
import com.deledzis.localshare.cache.db.dao.UsersDao
import com.deledzis.localshare.cache.db.model.CachedLocationPassword
import com.deledzis.localshare.cache.db.model.CachedUser

@Database(
    entities = [
        CachedLocationPassword::class,
        CachedUser::class
    ],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract fun locationPasswordsDao(): LocationPasswordsDao
    abstract fun usersDao(): UsersDao
}