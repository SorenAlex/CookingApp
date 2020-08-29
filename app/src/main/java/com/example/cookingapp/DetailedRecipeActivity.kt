package com.example.cookingapp

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.cookingapp.recipe.IngredientsAdapter
import kotlinx.android.synthetic.main.activity_detailed_recipe.*

class DetailedRecipeActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_recipe)

        val name = intent.getStringExtra("name")
        val feedNumber = intent.getIntExtra("feedNumber", 1)
        val cost = intent.getDoubleExtra("cost", 0.0)
        val difficulty = intent.getStringExtra("difficulty")
        val prepTime = intent.getIntExtra("prepTimeMin", 0)
        val ingredientsString = intent.getStringExtra("ingredients")

        text_detail_recipe_name.text = name
        text_detail_recipe_desc.text = "Feeds ${feedNumber} for ${cost}"
        text_detail_recipe_difficulty.text = "${difficulty} Difficulty"
        text_detail_recipe_prep_time.text = "Takes ${prepTime} mins"

        val ingredientsList = parseIngredients(ingredientsString)
        Log.d("ingre", ingredientsList.toString())
        val ingredientsAdapter = IngredientsAdapter(this, R.layout.ingredient_item, ingredientsList)
        val mIngredientsListView: ListView = findViewById(R.id.list_ingredients)
        mIngredientsListView.adapter = ingredientsAdapter
    }

    private fun parseIngredients(ingredientsString: String): List<String> {
        return ingredientsString.split("/")
    }
}