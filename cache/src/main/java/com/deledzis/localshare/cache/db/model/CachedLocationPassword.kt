package com.deledzis.localshare.cache.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deledzis.localshare.domain.model.LocationPassword

/**
 * Model used solely for the caching of a [LocationPassword]
 */
@Entity(tableName = "location_passwords")
data class CachedLocationPassword(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "password")
    val password: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "active")
    val active: Boolean,

    @ColumnInfo(name = "owner_id")
    val ownerId: Int
)