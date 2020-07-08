package com.deledzis.localshare.cache.db.dao

import androidx.room.*
import com.deledzis.localshare.cache.db.model.CachedUser

@Dao
interface UsersDao {

    @Transaction
    suspend fun setAuthenticatedUser(user: CachedUser) {
        deleteUsers()
        insertUser(user)
    }

    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getUser(): CachedUser

    @Insert
    suspend fun insertUser(user: CachedUser): Long

    @Update
    suspend fun updateUser(user: CachedUser): Int

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteUser(id: Int): Int

    @Query("DELETE FROM users")
    suspend fun deleteUsers(): Int
}