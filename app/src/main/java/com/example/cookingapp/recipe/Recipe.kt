package com.example.cookingapp.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_table")
data class Recipe(
    @PrimaryKey
    val name: String,

    var imageTag: String = "ic_placeholder",

    var feedNumber: Int = 1,

    var cost: Double = 12.50,

    var difficulty: String = "Easy",

    var prepTimeMin: Int = 50,

    var description: String = "Placeholder",

    var isFavourite: Boolean = false,

    var isComplete: Boolean = false,

    var ingredients: String = "nothing",

    var steps: String = "cry_/die"
)