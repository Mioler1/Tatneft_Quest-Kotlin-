package com.example.tatneftquest.FirstActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.tatneftquest.R
import com.google.android.material.tabs.TabLayout
import com.example.tatneftquest.Tablayout.PagerAdapter

class FirstScreenActivity : AppCompatActivity(){

    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen)

        init()
        setUpTabs()
    }

    private fun setUpTabs() {
        val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(AuthorizationFragment(), "Авторизация")
        adapter.addFragment(RegistrationFragment(), "Регистрация")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

    private fun init() {
        viewPager = findViewById(R.id.viewPager)
        tabs = findViewById(R.id.tabs)
    }
}