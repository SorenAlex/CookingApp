package com.example.cookingapp.recipe

import androidx.lifecycle.LiveData

class RecipeRepository(private val recipeDao: RecipeDao) {

    val recipes: LiveData<List<Recipe>> = recipeDao.getRecipes()

    suspend fun updateRecipe(recipe: Recipe) {
        recipeDao.updateRecipe(recipe)
    }

    suspend fun createRecipe(recipe: Recipe) {
        recipeDao.createRecipe(recipe)
    }
}