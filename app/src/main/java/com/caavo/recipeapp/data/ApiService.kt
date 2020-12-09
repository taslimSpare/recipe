package com.caavo.recipeapp.data

import com.caavo.recipeapp.models.RecipeObject
import retrofit2.http.GET


/*
This interface contains the endpoints that are being consuming directly from the API
*/
interface ApiService {

    // this method converts a value from one currency to another
    @GET("/")
    suspend fun fetchRecipes(): List<RecipeObject>

}
