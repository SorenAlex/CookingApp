package com.example.cookingapp.avatar.headPack

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "head_pack_table")
data class HeadPack(
    @PrimaryKey
    val name: String,

    val packImage: String = "ic_placeholder",

    var cost: Int = 20,

    var isBought: Boolean = false
)