package com.example.tatneftquest.Slider

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Display
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.example.tatneftquest.MainActivity
import com.example.tatneftquest.R
import me.relex.circleindicator.CircleIndicator3

class SliderActivity : AppCompatActivity() {

    private var titlesList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imagesList = mutableListOf<Int>()
    private lateinit var nextBtn: Button
    private lateinit var skipBtn: Button
    private lateinit var startBtn: Button
    private lateinit var viewPager2: ViewPager2
    private lateinit var indicator: CircleIndicator3


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slider)

        init()
        postToList()

        viewPager2.adapter = ViewPagerAdapter(titlesList, descList, imagesList)
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        indicator.setViewPager(viewPager2)


        nextBtn.setOnClickListener {
            viewPager2.apply {
                beginFakeDrag()
                fakeDragBy(-10f)
                endFakeDrag()
            }
            var count = 0
            count++
            if (count == 5) {
                skipBtn.visibility = View.GONE
                nextBtn.visibility = View.GONE
                startBtn.visibility = View.VISIBLE
            }
        }
        skipBtn.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
    }

    private fun init() {
        skipBtn = findViewById(R.id.skip)
        startBtn = findViewById(R.id.start)
        nextBtn = findViewById(R.id.next_slide)
        viewPager2 = findViewById(R.id.view_pager2)
        indicator = findViewById(R.id.indicator)
    }

    private fun addToList(title: String, description: String, image: Int) {
        titlesList.add(title)
        descList.add(description)
        imagesList.add(image)
    }

    private fun postToList() {
        for (i in 1..5) {
            when (i) {
                1 -> addToList("Title 111", "Description 111", R.mipmap.ic_launcher_round)
                2 -> addToList("Title 222", "Description 222", R.mipmap.ic_launcher_round)
                3 -> addToList("Title 333", "Description 333", R.mipmap.ic_launcher_round)
                4 -> addToList("Title 4", "Description 4", R.mipmap.ic_launcher_round)
                5 -> {
                    addToList("Title 5", "Description 5", R.mipmap.ic_launcher_round)
                }
            }
        }
    }

//    private fun loadLastScreen() {
//        skipBtn.visibility = View.GONE
//        nextBtn.visibility = View.GONE
//        startBtn.visibility = View.VISIBLE
//    }

}