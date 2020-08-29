package com.example.cookingapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.cookingapp.avatar.AvatarHeadFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_completion.*
import org.w3c.dom.Text

private const val NUM_PAGES = 3

class AvatarActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avatar)

        val mViewPager: ViewPager2 = findViewById(R.id.viewpager_customisation)
        val mTabLayout: TabLayout = findViewById(R.id.tabs_avatar)

        val mLevelBar: ProgressBar = findViewById(R.id.avatar_levelbar)
        val mLevelText: TextView = findViewById(R.id.avatar_level_text)
        val mCoinText: TextView = findViewById(R.id.avatar_coin_text)
        val mAvatarImage: ImageView = findViewById(R.id.main_avatar)

        val mPagerAdapter = AvatarPagerAdapter(this)
        mViewPager.adapter = mPagerAdapter

        TabLayoutMediator(mTabLayout, mViewPager) {tab, position ->
            when (position) {
                0 -> tab.text = "Hair"
                1 -> tab.text = "Background"
                2 -> tab.text = "Accessory"
            }
        }.attach()

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.currentUser.observe(this, Observer { user ->
            user?.let {
                mLevelBar.progress = ((it.currentExp/it.expToLevel)*1000).toInt()
                mCoinText.text = "${it.coins} coins"
                mLevelText.text = "Level ${it.level}"

                val avatarImage = ContextCompat.getDrawable(this, getDrawableIdByName(it.image))
                mAvatarImage.setImageDrawable(avatarImage)
            }
        })

    }

    private inner class AvatarPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> AvatarHeadFragment.newInstance()
                1 -> AvatarHeadFragment.newInstance()
                2 -> AvatarHeadFragment.newInstance()
                else -> AvatarHeadFragment.newInstance()
            }

        }
    }

}