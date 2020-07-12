package com.deledzis.localshare.cache.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.deledzis.localshare.cache.db.model.CachedLocationPassword

@Dao
interface LocationPasswordsDao {
    @Query("SELECT * FROM location_passwords WHERE owner_id = :userId")
    suspend fun getLocationPasswordsByUserId(userId: Int): List<CachedLocationPassword>

    @Query("SELECT * FROM location_passwords WHERE password = :password LIMIT 1")
    suspend fun getLocationPassword(password: String): CachedLocationPassword?

    @Insert
    suspend fun insertLocationPassword(locationPassword: CachedLocationPassword): Long

    @Insert
    suspend fun insertLocationPasswords(locationPasswords: List<CachedLocationPassword>): List<Long>

    @Update
    suspend fun updateLocationPassword(locationPassword: CachedLocationPassword): Int

    @Query("DELETE FROM location_passwords WHERE password = :password")
    suspend fun deleteLocationPassword(password: String): Int

    @Query("DELETE FROM location_passwords")
    suspend fun deleteLocationPasswords(): Int

    @Query("DELETE FROM location_passwords WHERE owner_id = :userId")
    suspend fun deleteLocationPasswordsByUserId(userId: Int): Int
}