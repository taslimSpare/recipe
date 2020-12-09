package com.caavo.recipeapp.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.caavo.recipeapp.models.UserObject
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Query("SELECT * FROM UserObject ORDER BY email ASC")
    fun getProfile(): Flow<List<UserObject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProfile(account: UserObject)

    @Query("DELETE FROM UserObject")
    suspend fun deleteProfile()
}