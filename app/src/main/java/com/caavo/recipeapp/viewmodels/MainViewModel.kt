package com.caavo.recipeapp.viewmodels

import androidx.lifecycle.*
import com.caavo.recipeapp.data.ApiService
import com.caavo.recipeapp.data.FirebaseHelper
import com.caavo.recipeapp.data.RoomDB
import com.caavo.recipeapp.models.*
import kotlinx.coroutines.launch
import java.lang.Exception

public class MainViewModel(

    val api: ApiService,
    val firebase: FirebaseHelper,
    val roomDB: RoomDB
) : ViewModel() {


    val cartItems = mutableListOf<RecipeObject>()



    private val marketLiveData = MutableLiveData<Resource<List<RecipeObject>>>()
    private val authStatus = MutableLiveData<Boolean>()


    // Room

    val getProfileFromRoom: LiveData<List<UserObject>> = roomDB.getAccount.asLiveData()
    val getMyRecipesFromRoom: LiveData<List<RecipeObject>> = roomDB.getRecipes.asLiveData()

    private fun deleteProfileFromRoom() = viewModelScope.launch { roomDB.deleteProfile() }


    fun fetchAllRecipes() {

        viewModelScope.launch {
            marketLiveData.postValue(Resource.loading())
            try {
                val result = api.fetchRecipes()
                marketLiveData.postValue(Resource.success(result))
            }
            catch (e: Exception) {
                marketLiveData.postValue(Resource.error())
            }
        }
    }


    fun buyRecipes(recipes: List<RecipeObject>) {

        viewModelScope.launch {
            roomDB.saveRecipes(recipes)
        }
    }


    fun logout() {
        firebase.signOut()
        deleteProfileFromRoom()
    }


    fun getMarketRecipeLiveData() = marketLiveData


}
