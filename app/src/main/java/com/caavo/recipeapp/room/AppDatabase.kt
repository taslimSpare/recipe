package com.caavo.recipeapp.room


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.caavo.recipeapp.models.RecipeObject
import com.caavo.recipeapp.models.UserObject
import com.caavo.recipeapp.room.dao.AccountDao
import com.caavo.recipeapp.room.dao.MyItemsDao


@Database(entities = [UserObject::class, RecipeObject::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun myItemsDao(): MyItemsDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "recipe_db"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}