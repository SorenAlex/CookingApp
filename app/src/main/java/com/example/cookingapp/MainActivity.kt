package com.example.cookingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
}