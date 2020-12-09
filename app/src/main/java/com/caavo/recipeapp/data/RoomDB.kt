package com.caavo.recipeapp.data

import androidx.annotation.WorkerThread
import com.caavo.recipeapp.models.RecipeObject
import com.caavo.recipeapp.models.UserObject
import com.caavo.recipeapp.room.dao.AccountDao
import com.caavo.recipeapp.room.dao.MyItemsDao
import kotlinx.coroutines.flow.Flow

/*
This class stores all the local database (Room) operations.
As represented here, this class focuses on creating, fetching, updating and deleting a user's profile
 */
class RoomDB(
    private val accountDao: AccountDao,
    private val myItemsDao: MyItemsDao
) {



    /* This returns a LiveData of the current account(s) stored in the DB.
        I chose to use a LiveData because of the advantage of always getting real-time data
     */
    val getAccount: Flow<List<UserObject>> = accountDao.getProfile()



    // this function saves an a user's account to local storage (Room)
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: UserObject) {
        accountDao.saveProfile(word)
    }



    // this function deletes a/all user profiles from the local storage
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteProfile() {
        accountDao.deleteProfile()
    }


    // this function saves an a user's account to local storage (Room)
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun saveRecipes(recipes: List<RecipeObject>) {
        myItemsDao.addItems(recipes)
    }


    /* This returns a LiveData of the current account(s) stored in the DB.
        I chose to use a LiveData because of the advantage of always getting real-time data
     */
    val getRecipes: Flow<List<RecipeObject>> = myItemsDao.getAllItems()


}