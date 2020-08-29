package com.example.cookingapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.cookingapp.recipe.IngredientsAdapter
import com.example.cookingapp.recipe.StepsAdapter
import kotlinx.android.synthetic.main.activity_detailed_recipe.*

class DetailedRecipeActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_recipe)

        val description = intent.getStringExtra("description")
        val name = intent.getStringExtra("name")
        val imageTag = intent.getStringExtra("imageTag")
        val feedNumber = intent.getIntExtra("feedNumber", 1)
        val cost = intent.getDoubleExtra("cost", 0.0)
        val difficulty = intent.getStringExtra("difficulty")
        val prepTime = intent.getIntExtra("prepTimeMin", 0)
        val ingredientsString = intent.getStringExtra("ingredients")
        val stepsString = intent.getStringExtra("steps")

        text_detail_recipe_name.text = name
        text_detail_recipe_desc.text = description
        text_detail_recipe_difficulty.text = "Difficulty: ${difficulty}"
        text_detail_recipe_prep_time.text = "Est. Time: ${prepTime} mins"

        val mRecipeImage: ImageView = findViewById(R.id.recipe_image_detailed)
        val packImage = ContextCompat.getDrawable(this, getDrawableIdByName(imageTag))
        mRecipeImage.setImageDrawable(packImage)

        val ingredientsList = parseIngredients(ingredientsString)
        //val ingredientsAdapter = IngredientsAdapter(this, R.layout.ingredient_item, ingredientsList)
        val mIngredientsListView: LinearLayout = findViewById(R.id.list_ingredients)
        //populate ingredients
        for (ingredient in ingredientsList) {
            val checkBox = CheckBox(this)
            checkBox.text = ingredient
            mIngredientsListView.addView(checkBox)
        }

        val stepsList = parseSteps(stepsString)
        //val stepsAdapter = StepsAdapter(this, R.layout.step_item, stepsList)
        val mStepsListView: LinearLayout = findViewById(R.id.list_steps)
        //populate steps
        var i = 1
        for (step in stepsList) {
            val checkBox = CheckBox(this)
            checkBox.text = "Step ${i}"
            i++
            mStepsListView.addView(checkBox)

            val text = TextView(this)
            text.text = step
            text.setPadding(72, 0,0, 0)
            mStepsListView.addView(text)
        }


        val mRecipeCompleteButton: Button = findViewById(R.id.button_recipe_complete)
        mRecipeCompleteButton.setOnClickListener {
            val intent = Intent(this@DetailedRecipeActivity, CompletionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun parseIngredients(ingredientsString: String): List<String> {
        return ingredientsString.split("_/")
    }

    private fun parseSteps(stepsString: String): List<String> {
        return stepsString.split("_/")
    }
}