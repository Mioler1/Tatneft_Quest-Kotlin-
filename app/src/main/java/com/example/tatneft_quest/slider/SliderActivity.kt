package com.example.tatneft_quest.slider

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.tatneft_quest.MainActivity
import com.example.tatneft_quest.R
import com.example.tatneft_quest.databinding.ActivitySliderBinding
import com.google.android.material.tabs.TabLayout
import java.util.*

class SliderActivity : AppCompatActivity() {
    lateinit var mBinding: ActivitySliderBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var mTabLayout: TabLayout
    private lateinit var onBoardingViewPager: ViewPager
    private lateinit var mNext: Button
    private lateinit var mSkip: Button
    private lateinit var sharedPreferences: SharedPreferences
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySliderBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        init()

        if (restorePrefData()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val onBoardingData: MutableList<ViewPagerAdapter.OnBoardingData> = ArrayList()
        onBoardingData.add(ViewPagerAdapter.OnBoardingData("Выбирай квест или экскурсию",
            R.drawable.icon1))
        onBoardingData.add(ViewPagerAdapter.OnBoardingData("Следуй маршруту", R.drawable.icon2))
        onBoardingData.add(ViewPagerAdapter.OnBoardingData("Сканируй QR-code", R.drawable.icon3))
        onBoardingData.add(ViewPagerAdapter.OnBoardingData("Читай историю", R.drawable.icon4))
        onBoardingData.add(ViewPagerAdapter.OnBoardingData("Выполняй задания", R.drawable.icon5))
        onBoardingData.add(ViewPagerAdapter.OnBoardingData("Меняй баллы на подарки",
            R.drawable.icon6))

        setViewPagerAdapter(onBoardingData)
        position = onBoardingViewPager.currentItem

        mNext.setOnClickListener {
            if (position < onBoardingData.size) {
                position++
                onBoardingViewPager.currentItem = position
            }
            if (position == onBoardingData.size) {
                savePrefData()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        mSkip.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                position = tab!!.position
                if (tab.position == onBoardingData.size - 1) {
                    mNext.text = "Старт!"
                } else {
                    mNext.text = "Дальше"
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun init() {
        mTabLayout = mBinding.indicator
        mNext = mBinding.next
        onBoardingViewPager = mBinding.screenPager
        mSkip = mBinding.skip
    }

    private fun setViewPagerAdapter(onBoardingData: List<ViewPagerAdapter.OnBoardingData>) {
        viewPagerAdapter = ViewPagerAdapter(this, onBoardingData)
        onBoardingViewPager.adapter = viewPagerAdapter
        mTabLayout.setupWithViewPager(onBoardingViewPager)
    }

    private fun savePrefData() {
        sharedPreferences = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isFirstTimeRun", true)
        editor.apply()
    }

    private fun restorePrefData(): Boolean {
        sharedPreferences = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isFirstTimeRun", false)
    }

}