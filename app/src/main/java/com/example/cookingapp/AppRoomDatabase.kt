package com.example.cookingapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cookingapp.avatar.headPack.HeadPack
import com.example.cookingapp.avatar.headPack.HeadPackDao
import com.example.cookingapp.recipe.Recipe
import com.example.cookingapp.recipe.RecipeDao
import com.example.cookingapp.user.User
import com.example.cookingapp.user.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [Recipe::class, HeadPack::class, User::class],
    version = 1,
    exportSchema = false)
public abstract class AppRoomDatabase : RoomDatabase() {
    
    abstract fun recipeDao(): RecipeDao
    abstract fun headPackDao(): HeadPackDao
    abstract fun userDao(): UserDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateRecipeTable(database.recipeDao())
                    populateHeadPackTable(database.headPackDao())
                    populateUserTable(database.userDao())
                }
            }
        }

        suspend fun populateRecipeTable(recipeDao: RecipeDao) {
            var recipe = Recipe("Chocolate Chip Cookies")
            recipe.difficulty = "Easy"
            recipe.imageTag = "cookies"
            recipeDao.createRecipe(recipe)

            recipe = Recipe("Spaghetti Carbonara")
            recipe.difficulty = "Easy"
            recipe.imageTag = "carbonara"
            recipeDao.createRecipe(recipe)

            recipe = Recipe("Dumplings")
            recipe.difficulty = "Easy"
            recipe.imageTag = "dumps"
            recipeDao.createRecipe(recipe)

            recipe = Recipe("Banana Bread")
            recipe.feedNumber = 2
            recipe.difficulty = "Easy"
            recipe.description="Banana bread made right. No fire alarms, no tragic banana sandwich. Just wholesome banana bread and happiness."
            recipe.ingredients = "2 cups all-purpose flour_/#flour_/1 tsp baking soda_/#baking_powder_/1/4 tsp salt_/#salt_/1/2 cup unsalted butter at room temperature_/#butter_/3/4 cup dark brown sugar_/#brown_sugar_/2 large eggs, at room temperature_/#egg_/4 bananas(mashed)_/#banana_/1 tsp pure vanilla extract_/#vanila_/"
            recipe.imageTag = "bananabread"
            recipe.steps = "Preheat the oven to 350°F (177°C)._/Grease a 9×5-inch loaf pan or coat with nonstick spray. Set aside._/Whisk the flour, baking soda and salt together in a large bowl. These are the dry ingredients._/Mix the butter and brown sugar together in another large bowl until smooth and creamy._/Add the eggs one at a time, mixing well after each addition._/Add mashed bananas and vanilla extract, mixing until well combined._/Gradually add the dry ingredients into the wet ingredients until no flour pockets remain but do not overmix._/Spoon the batter into the prepared baking pan and bake for 60-65 minutes. Loosely cover the bread with aluminum foil after 30 minutes to help prevent the top and sides from getting too brown._/Remove from the oven and allow the banana bread to cool completely."
            recipeDao.createRecipe(recipe)

            recipe = Recipe("Blueberry Muffins")
            recipe.difficulty = "Easy"
            recipe.imageTag = "muff"
            recipeDao.createRecipe(recipe)
        }

        suspend fun populateHeadPackTable(headPackDao: HeadPackDao) {
            var headPack = HeadPack("hairAang")
            headPack.packImage = "hair_aang"
            headPackDao.createHeadPack(headPack)

            headPack = HeadPack("hairKyoshi")
            headPack.packImage = "hair_kyoshi"
            headPackDao.createHeadPack(headPack)

            headPack = HeadPack("bgFry")
            headPack.packImage = "bg_fry"
            headPack.packSet = "bg"
            headPackDao.createHeadPack(headPack)

            headPack = HeadPack("bgHat")
            headPack.packImage = "bg_hat"
            headPack.packSet = "bg"
            headPackDao.createHeadPack(headPack)
        }

        suspend fun populateUserTable(userDao: UserDao) {
            val user = User("current")
            user.coins = 10
            userDao.createUser(user)
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