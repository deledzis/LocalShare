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

    @Query("SELECT * FROM location_passwords WHERE id = :id LIMIT 1")
    suspend fun getLocationPassword(id: Int): CachedLocationPassword?

    @Insert
    suspend fun insertLocationPassword(locationPassword: CachedLocationPassword): Long

    @Insert
    suspend fun insertLocationPasswords(locationPasswords: List<CachedLocationPassword>): List<Long>

    @Update
    suspend fun updateLocationPassword(locationPassword: CachedLocationPassword): Int

    @Query("DELETE FROM location_passwords WHERE id = :id")
    suspend fun deleteLocationPassword(id: Int): Int

    @Query("DELETE FROM location_passwords")
    suspend fun deleteLocationPasswords(): Int

    @Query("DELETE FROM location_passwords WHERE owner_id = :userId")
    suspend fun deleteLocationPasswordsByUserId(userId: Int): Int
}