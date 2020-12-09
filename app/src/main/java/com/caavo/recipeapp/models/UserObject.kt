package com.caavo.recipeapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserObject(
    @PrimaryKey val email: String = "",
    val name: String = ""
)
