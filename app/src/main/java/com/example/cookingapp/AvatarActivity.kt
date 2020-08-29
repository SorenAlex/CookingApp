package com.example.cookingapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_completion.*

class AvatarActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avatar)

//        val mViewPager: ViewPager2 = findViewById(R.id.viewpager_customisation)
//        val mTabLayout: TabLayout = findViewById(R.id.tabs_avatar)
//
//        val mPagerAdapter = AvatarPagerAdapter(this)
//        mViewPager.adapter = mPagerAdapter
//
//        TabLayoutMediator(mTabLayout, mViewPager) {tab, position ->
//            when (position) {
//                0 -> tab.text = "Head"
//                1 -> tab.text = "Top"
//                2 -> tab.text = "Bottom"
//            }
//        }.attach()

    }

}