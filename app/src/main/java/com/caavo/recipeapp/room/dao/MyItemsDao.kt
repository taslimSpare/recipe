package com.caavo.recipeapp.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.caavo.recipeapp.models.RecipeObject
import kotlinx.coroutines.flow.Flow

@Dao
interface MyItemsDao {

    @Query("SELECT * FROM RecipeObject ORDER BY name ASC")
    fun getAllItems(): Flow<List<RecipeObject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItems(item: List<RecipeObject>)

    @Query("DELETE FROM RecipeObject")
    suspend fun deleteAllItems()

}