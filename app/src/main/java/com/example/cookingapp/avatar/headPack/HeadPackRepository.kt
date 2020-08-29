package com.example.cookingapp.avatar.headPack

import androidx.lifecycle.LiveData
import com.example.cookingapp.avatar.headPack.HeadPack
import com.example.cookingapp.avatar.headPack.HeadPackDao

class HeadPackRepository(private val headPackDao: HeadPackDao) {

    val headPacks: LiveData<List<HeadPack>> = headPackDao.getHeadPacks()

    suspend fun updateHeadPack(headPack: HeadPack) {
        headPackDao.updateHeadPack(headPack)
    }

    suspend fun createHeadPack(headPack: HeadPack) {
        headPackDao.createHeadPack(headPack)
    }
}