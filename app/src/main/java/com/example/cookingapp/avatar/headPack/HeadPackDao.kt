package com.example.cookingapp.avatar.headPack

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HeadPackDao {

    @Query("SELECT * FROM head_pack_table")
    fun getHeadPacks(): LiveData<List<HeadPack>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createHeadPack(headPack: HeadPack)

    @Update
    suspend fun updateHeadPack(headPack: HeadPack)

    @Query("DELETE FROM head_pack_table")
    suspend fun deleteAllHeadPacks()
}