package com.example.cookingapp

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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

fun decodeSampledBitmapFromResource(
    res: Resources,
    resId: Int,
    reqWidth: Int,
    reqHeight: Int
): Bitmap {
    // First decode with inJustDecodeBounds=true to check dimensions
    return BitmapFactory.Options().run {
        inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, this)

        // Calculate inSampleSize
        inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)

        // Decode bitmap with inSampleSize set
        inJustDecodeBounds = false

        BitmapFactory.decodeResource(res, resId, this)
    }
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    // Raw height and width of image
    val (height: Int, width: Int) = options.run { outHeight to outWidth }
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {

        val halfHeight: Int = height / 2
        val halfWidth: Int = width / 2

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}

fun makeToast(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


fun updateCoinText(user: User, coinText: TextView) {
    coinText.text = (user.coins).toString() + " coins"
}

fun setupAvatarImage(user: User, image: ImageView, mainViewModel: MainViewModel): String {
    val hair = user.hair
    val bg = user.background

    return if (bg == "white") {
        "aang"
    } else return if (bg == "hat" && hair == "aang") {
        "aang_hat"
    } else return if (bg == "hat" && hair == "kyoshi") {
        "kyoshi_hat"
    } else return if (bg == "fry" && hair == "aang") {
        "aang_fry"
    } else return if (bg == "fry" && hair == "kyoshi") {
        "kyoshi_fry"
    } else {
        return "aang"
    }

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
