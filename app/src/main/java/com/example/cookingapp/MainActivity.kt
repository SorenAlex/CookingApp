package com.example.cookingapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingapp.recipe.RecipeAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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

        recipeAdapter.onItemClick = {recipe ->
            val intent = Intent(this@MainActivity, DetailedRecipeActivity::class.java)
            intent.putExtra("name",recipe.name)
            intent.putExtra("feedNumber",recipe.feedNumber)
            intent.putExtra("cost",recipe.cost)
            intent.putExtra("difficulty",recipe.difficulty)
            intent.putExtra("prepTimeMin",recipe.prepTimeMin)
            intent.putExtra("ingredients",recipe.ingredients)
            intent.putExtra("steps",recipe.steps)
            startActivity(intent)
        }

        val mAvatarImage: ImageView = findViewById(R.id.main_avatar)
        mAvatarImage.setOnClickListener {
            val intent = Intent(this@MainActivity, AvatarActivity::class.java)
            startActivity(intent)
        }
    }
}