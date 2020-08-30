package com.example.cookingapp.avatar.headPack

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cookingapp.MainViewModel

@Entity(tableName = "head_pack_table")
data class HeadPack(
    @PrimaryKey
    val name: String,

    var packImage: String = "ic_placeholder",

    var packSet: String = "hair",

    var cost: Int = 20,

    var isBought: Boolean = false
) {
    fun buy(mainViewModel: MainViewModel) {
        isBought = true


        setUser(mainViewModel, false)
    }

    fun setUser(mainViewModel: MainViewModel, isSet: Boolean) {
        when (name) {
            "hairAang" -> {
                val user = mainViewModel.currentUser.value

                if (user != null) {
                    user.hair = "aang"
                    if (!isSet) {
                        user.coins -= 20
                    }
                    mainViewModel.updateUser(user)
                }
            }
            "hairKyoshi" -> {
                val user = mainViewModel.currentUser.value

                if (user != null) {
                    user.hair = "kyoshi"
                    if (!isSet) {
                        user.coins -= 20
                    }
                    mainViewModel.updateUser(user)
                }
            }
            "bgFry" -> {
                val user = mainViewModel.currentUser.value

                Log.d("avatar", "bgfry set")

                if (user != null) {
                    user.background = "fry"
                    if (!isSet) {
                        user.coins -= 20
                    }
                    mainViewModel.updateUser(user)
                }
            }
            "bgHat" -> {
                val user = mainViewModel.currentUser.value

                if (user != null) {
                    user.background = "hat"
                    if (!isSet) {
                        user.coins -= 20
                    }
                    mainViewModel.updateUser(user)
                }
            }
        }
    }
}