package com.caavo.recipeapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeObject(
    @PrimaryKey val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val category: String = "",
    val label: String = "",
    val price: String = "",
    val description: String = "",
    var isInCart: Boolean = false
)
