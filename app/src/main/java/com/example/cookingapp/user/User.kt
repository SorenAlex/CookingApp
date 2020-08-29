package com.example.cookingapp.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "level")
    var level: Int= 1,

    var image: String = "aang",

    var hair: String = "aang",

    var background: String = "white",

    @ColumnInfo(name = "current_exp")
    var currentExp: Double = 0.0,

    @ColumnInfo(name = "exp_to_level")
    var expToLevel: Double = 30.0,

    @ColumnInfo(name = "last_level")
    var lastLevel: Int = 1,

    @ColumnInfo(name = "last_exp")
    var lastExp: Double = 0.0,

    @ColumnInfo(name = "exp_to_last_level")
    var expToLastLevel: Double = 0.0,

    @ColumnInfo(name = "coins")
    var coins: Long = 0,
) {
    fun addExp(expDelta: Double) {
        var exp = expDelta
        updateLast()
        currentExp += exp
        while (currentExp >= expToLevel) {
            currentExp -= expToLevel
            expToLevel = nextLevel(expToLevel)
            level++
        }
    }

    private fun updateLast() {
        lastLevel = level
        lastExp = currentExp
        expToLastLevel = expToLevel
    }

    fun addCoins(delta: Int) {
        coins += delta
    }
}