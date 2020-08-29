package com.example.cookingapp

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

        val mReturnButton: Button = findViewById(R.id.button_return)
        mReturnButton.setOnClickListener {
            finish()
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