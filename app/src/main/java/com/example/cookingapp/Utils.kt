package com.example.cookingapp

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.cookingapp.user.User

fun getDrawableIdByName(name: String) = try {
    R.drawable::class.java.getField(name).getInt(null)
} catch (e: NoSuchFieldError) {
    error("No drawable with name $name")
}

fun makeToast(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


fun updateCoinText(user: User, coinText: TextView) {
    coinText.text = (user.coins).toString() + " coins"
}

fun setupAvatarImage(user: User, image: ImageView, mainViewModel: MainViewModel) {
    val hair = user.hair
    val bg = user.background

    if (bg == "white") {
        user.image = "aang"
    } else if (bg == "hat" && hair == "aang") {
        user.image = "aang_hat"
    } else if (bg == "hat" && hair == "kyoshi") {
        user.image = "kyoshi_hat"
    } else if (bg == "fry" && hair == "aang") {
        user.image = "aang_fry"
    } else if (bg == "fry" && hair == "kyoshi") {
        user.image = "kyoshi_fry"
    }

    mainViewModel.updateUser(user)
}

fun updateLevelBar(user: User, mainViewModel: MainViewModel,
                   mLevelBar: ProgressBar, mLevelText: TextView) {

    var lastExp = user.lastExp
    var expToLastLevel = user.expToLastLevel
    val currentExp = user.currentExp
    val expToLevel = user.expToLevel
    var level = user.level

    if (mainViewModel.userNeedUpdate()) {

        var numOfLevelUps = level-user.lastLevel
        level -= numOfLevelUps

        val animationList : MutableList<ObjectAnimator> = mutableListOf()
        for (i in 0..numOfLevelUps) {
            val progressAnimator : ObjectAnimator
            when (i) {
                0 -> {
                    val lastBarState: Int = (lastExp/expToLastLevel*1000).toInt()
                    progressAnimator = ObjectAnimator.ofInt(
                        mLevelBar, "progress",
                        lastBarState, 1000
                    )

                    progressAnimator.addListener(object : Animator.AnimatorListener {
                        override fun onAnimationRepeat(animation: Animator?) {}
                        override fun onAnimationEnd(animation: Animator?) {
                            level += 1
                            mLevelText.text = "Level " + level
                        }
                        override fun onAnimationCancel(animation: Animator?) {}
                        override fun onAnimationStart(animation: Animator?) {}
                    })
                }
                numOfLevelUps -> {
                    val newBarState: Int = (currentExp/expToLevel*1000).toInt()
                    progressAnimator = ObjectAnimator.ofInt(
                        mLevelBar, "progress",
                        0, newBarState
                    )
                }
                else -> {
                    progressAnimator = ObjectAnimator.ofInt(
                        mLevelBar, "progress",
                        0, 1000
                    )

                    progressAnimator.addListener(object : Animator.AnimatorListener {
                        override fun onAnimationRepeat(animation: Animator?) {}
                        override fun onAnimationEnd(animation: Animator?) {
                            level += 1
                            mLevelText.text = "Level " + level
                        }
                        override fun onAnimationCancel(animation: Animator?) {}
                        override fun onAnimationStart(animation: Animator?) {}
                    })
                }
            }

            progressAnimator.duration = 500
            progressAnimator.startDelay = (i*500).toLong()
            animationList.add(progressAnimator)
        }
        val animationSet = AnimatorSet()
        if (numOfLevelUps > 0) {
            for (i in 0 until animationList.size) {
                animationSet.play(animationList[i])
            }
            animationSet.start()
        } else {
            val lastBarState: Int = (lastExp/expToLastLevel*1000).toInt()
            val newBarState: Int = (currentExp/expToLevel*1000).toInt()

            val progressAnimator = ObjectAnimator.ofInt(
                mLevelBar, "progress",
                lastBarState, newBarState
            )

            progressAnimator.duration = 500
            progressAnimator.start()
        }
        mainViewModel.closeUserNeedUpdate()

    } else {
        mLevelBar.progress = (currentExp/expToLevel*1000).toInt()
        mLevelText.text = "Level " + level
    }
}
