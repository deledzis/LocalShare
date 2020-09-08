package com.deledzis.localshare.cache.db.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.deledzis.localshare.domain.model.LastCoordinates
import com.deledzis.localshare.domain.model.TrackingPassword

/**
 * Model used solely for the caching of a [TrackingPassword]
 */
@Entity(tableName = "tracking_passwords")
data class CachedTrackingPassword(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "password")
    val password: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "active")
    val active: Boolean,

    @Embedded
    val lastCoordinates: LastCoordinates,

    @ColumnInfo(name = "last_update_time")
    val lastUpdateTime: Long
)