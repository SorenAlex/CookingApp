package com.example.cookingapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cookingapp.avatar.headPack.HeadPack
import com.example.cookingapp.avatar.headPack.HeadPackRepository
import com.example.cookingapp.recipe.Recipe
import com.example.cookingapp.recipe.RecipeRepository
import com.example.cookingapp.user.User
import com.example.cookingapp.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val userNeedUpdateBool = MutableLiveData<Boolean>()

    private val recipeRepository: RecipeRepository
    val currentRecipes: LiveData<List<Recipe>>

    private val headPackRepository: HeadPackRepository
    val currentHeadPacks: LiveData<List<HeadPack>>

    private val userRepository: UserRepository
    val currentUser: LiveData<User>

    init {
        userNeedUpdateBool.value = false

        val recipeDao = AppRoomDatabase.getDatabase(application, viewModelScope).recipeDao()
        recipeRepository = RecipeRepository(recipeDao)
        currentRecipes = recipeRepository.recipes

        val headPackDao = AppRoomDatabase.getDatabase(application, viewModelScope).headPackDao()
        headPackRepository = HeadPackRepository(headPackDao)
        currentHeadPacks = headPackRepository.headPacks

        val userDao = AppRoomDatabase.getDatabase(application, viewModelScope).userDao()
        userRepository = UserRepository(userDao)
        currentUser = userRepository.userState
    }

    fun updateRecipe(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        recipeRepository.updateRecipe(recipe)
    }

    fun updateUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.update(user)
    }

    // User update state functions
    fun notifyUserNeedUpdate() {
        userNeedUpdateBool.value = true
    }
    fun closeUserNeedUpdate() {
        userNeedUpdateBool.value = false
    }
    fun userNeedUpdate(): Boolean {
        return userNeedUpdateBool.value!!
    }
}