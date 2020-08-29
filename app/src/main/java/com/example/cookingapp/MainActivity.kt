package com.example.cookingapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingapp.recipe.RecipeAdapter
import com.example.cookingapp.user.User

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var userState: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var exp = intent.getDoubleExtra("exp",0.0)
        var coins = intent.getIntExtra("coins", 0)

        val mCoinText: TextView = findViewById(R.id.main_coin_text)
        val mLevelText: TextView = findViewById(R.id.main_level_text)
        val mLevelBar: ProgressBar = findViewById(R.id.main_level_bar)
        val mAvatar: ImageView = findViewById(R.id.landing_avatar)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_recipes)
        val recipeAdapter = RecipeAdapter(this)
        recyclerView.adapter = recipeAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.currentRecipes.observe(this, Observer { recipes ->
            recipes?.let {
                recipeAdapter.setRecipes(it)
            }
        })
        mainViewModel.currentUser.observe(this, Observer { user ->
            user?.let {
                userState = it

                if (exp > 0) {
                    userState.addExp(exp)
                    mainViewModel.updateUser(userState)
                    mainViewModel.notifyUserNeedUpdate()

                    exp = 0.0
                }

                if (coins > 0) {
                    userState.addCoins(coins)
                    mainViewModel.updateUser(userState)

                    coins = 0
                }


                val avatarImage = ContextCompat.getDrawable(this, getDrawableIdByName(it.image))
                mAvatar.setImageDrawable(avatarImage)

                updateLevelBar(it, mainViewModel, mLevelBar, mLevelText)
                updateCoinText(it, mCoinText)
            }
        })

        recipeAdapter.onItemClick = {recipe ->
            val intent = Intent(this@MainActivity, DetailedRecipeActivity::class.java)
            intent.putExtra("description",recipe.description)
            intent.putExtra("name",recipe.name)
            intent.putExtra("imageTag",recipe.imageTag)
            intent.putExtra("feedNumber",recipe.feedNumber)
            intent.putExtra("cost",recipe.cost)
            intent.putExtra("difficulty",recipe.difficulty)
            intent.putExtra("prepTimeMin",recipe.prepTimeMin)
            intent.putExtra("ingredients",recipe.ingredients)
            intent.putExtra("steps",recipe.steps)
            startActivity(intent)
        }

        val mAvatarImage: ImageView = findViewById(R.id.landing_avatar)
        mAvatarImage.setOnClickListener {
            val intent = Intent(this@MainActivity, AvatarActivity::class.java)
            startActivity(intent)
        }


    }
}