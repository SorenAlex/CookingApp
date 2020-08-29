package com.example.cookingapp

fun getDrawableIdByName(name: String) = try {
    R.drawable::class.java.getField(name).getInt(null)
} catch (e: NoSuchFieldError) {
    error("No drawable with name $name")
}