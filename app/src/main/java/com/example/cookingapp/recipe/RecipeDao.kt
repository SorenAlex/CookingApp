package com.example.cookingapp.recipe

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipe_table")
    fun getRecipes(): LiveData<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createRecipe(recipe: Recipe)

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Query("DELETE FROM recipe_table")
    suspend fun deleteAllRecipes()
}