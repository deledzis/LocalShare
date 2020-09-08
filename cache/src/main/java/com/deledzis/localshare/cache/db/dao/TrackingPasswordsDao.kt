package com.deledzis.localshare.cache.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.deledzis.localshare.cache.db.model.CachedTrackingPassword

@Dao
interface TrackingPasswordsDao {
    @Query("SELECT * FROM tracking_passwords")
    suspend fun getTrackingPasswords(): List<CachedTrackingPassword>

    @Query("SELECT * FROM tracking_passwords WHERE password = :password LIMIT 1")
    suspend fun getTrackingPassword(password: String): CachedTrackingPassword?

    @Insert
    suspend fun insertTrackingPassword(trackingPassword: CachedTrackingPassword): Long

    @Insert
    suspend fun insertTrackingPasswords(trackingPasswords: List<CachedTrackingPassword>): List<Long>

    @Update
    suspend fun updateTrackingPassword(trackingPassword: CachedTrackingPassword): Int

    @Query("DELETE FROM tracking_passwords WHERE password = :password")
    suspend fun deleteTrackingPassword(password: String): Int

    @Query("DELETE FROM tracking_passwords")
    suspend fun deleteTrackingPasswords(): Int
}