package com.example.cookingapp

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cookingapp.recipe.IngredientsAdapter
import com.example.cookingapp.recipe.StepsAdapter
import kotlinx.android.synthetic.main.activity_completion.*
import kotlinx.android.synthetic.main.activity_detailed_recipe.*

class CompletionActivity : AppCompatActivity() {

    val CAMERA_REQUEST_CODE = 0

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completion)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mainViewModel.currentRecipes.observe(this, Observer { recipes ->
            recipes?.let {
                val recipeCompleted = intent.getStringExtra("recipeCompleted")
                for (recipe in it) {
                    if (recipe.name == recipeCompleted) {
                        Log.d("testcomplete", "reached")
                        recipe.isComplete = true
                        mainViewModel.updateRecipe(recipe)
                    }
                }
            }

        })

        val mReturnButton: Button = findViewById(R.id.button_return)
        mReturnButton.setOnClickListener {
            val intent = Intent(this@CompletionActivity, MainActivity::class.java)

            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
            intent.putExtra("exp", 50.0)
            intent.putExtra("coins", 12)
            startActivity(intent)
        }

        val mPhotoButton: Button = findViewById(R.id.button_save_photo)
        mPhotoButton.setOnClickListener {
            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (callCameraIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(callCameraIntent, CAMERA_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    recipe_img.setImageBitmap(data.extras?.get("data") as Bitmap)
                }
            }
            else -> {
                Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_SHORT)
            }
        }
    }
}