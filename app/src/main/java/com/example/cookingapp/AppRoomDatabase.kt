package com.example.cookingapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cookingapp.recipe.Recipe
import com.example.cookingapp.recipe.RecipeDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [Recipe::class],
    version = 1,
    exportSchema = false)
public abstract class AppRoomDatabase : RoomDatabase() {
    
    abstract fun recipeDao(): RecipeDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateRecipeTable(database.recipeDao())
                }
            }
        }

        suspend fun populateRecipeTable(recipeDao: RecipeDao) {
            var recipe = Recipe("Dumplings",)
            recipeDao.createRecipe(recipe)
            recipe = Recipe("Steak")
            recipe.feedNumber = 2
            recipe.difficulty = "Medium"
            recipeDao.createRecipe(recipe)
            recipe = Recipe("Ginger Chicken")
            recipe.cost = 15.55
            recipeDao.createRecipe(recipe)
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppRoomDatabase::class.java,
                    "user_database"
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}