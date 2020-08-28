package com.example.cookingapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.recipe.Recipe
import com.example.cookingapp.recipe.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val recipeRepository: RecipeRepository
    val currentRecipes: LiveData<List<Recipe>>

    init {
        val recipeDao = AppRoomDatabase.getDatabase(application, viewModelScope).recipeDao()
        recipeRepository = RecipeRepository(recipeDao)
        currentRecipes = recipeRepository.recipes
    }

    fun updateRecipe(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        recipeRepository.updateRecipe(recipe)
    }
}