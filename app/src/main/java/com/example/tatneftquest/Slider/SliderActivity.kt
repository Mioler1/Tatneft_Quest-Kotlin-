package com.example.tatneftquest.Slider

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.tatneftquest.MainActivity
import com.example.tatneftquest.R
import com.google.android.material.tabs.TabLayout
import java.util.*

class SliderActivity() : AppCompatActivity() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var onBoardingViewPager: ViewPager
    private lateinit var next: Button
    private lateinit var skip: Button
    private lateinit var sharedPreferences: SharedPreferences
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slider)
        init()

        if (restorePrefData()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val onBoardingData: MutableList<OnBoardingData> = ArrayList()
        onBoardingData.add(OnBoardingData("Выбирай квест или экскурсию", R.drawable.icon1))
        onBoardingData.add(OnBoardingData("Следуй маршруту", R.drawable.icon2))
        onBoardingData.add(OnBoardingData("Сканируй QR-code", R.drawable.icon3))
        onBoardingData.add(OnBoardingData("Читай историю", R.drawable.icon4))
        onBoardingData.add(OnBoardingData("Выполняй задания", R.drawable.icon5))
        onBoardingData.add(OnBoardingData("Меняй баллы на подарки", R.drawable.icon6))

        setViewPagerAdapter(onBoardingData)

        position = onBoardingViewPager.currentItem

        next.setOnClickListener {
            if (position < onBoardingData.size) {
                position++
                onBoardingViewPager.currentItem = position
            }
            if (position == onBoardingData.size) {
                savePrefData()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        skip.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                position = tab!!.position
                if (tab.position == onBoardingData.size - 1) {
                    next.text = "Старт!"
                } else {
                    next.text = "Дальше"
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun init() {
        tabLayout = findViewById(R.id.indicator)
        next = findViewById(R.id.next)
        onBoardingViewPager = findViewById(R.id.screenPager)
        skip = findViewById(R.id.skip)
    }

    private fun setViewPagerAdapter(onBoardingData: List<OnBoardingData>) {
        viewPagerAdapter = ViewPagerAdapter(this, onBoardingData)
        onBoardingViewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(onBoardingViewPager)
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