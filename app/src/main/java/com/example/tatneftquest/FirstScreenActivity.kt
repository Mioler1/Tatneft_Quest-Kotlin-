package com.example.tatneftquest

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.tatneftquest.fragments.AuthorizationFragment
import com.example.tatneftquest.fragments.RegistrationFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import gaur.himanshu.august.tablayout.adapters.ViewPagerAdapterForTablayout
import me.relex.circleindicator.CircleIndicator3

class FirstScreenActivity : AppCompatActivity(){

    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)

        init()
        setUpTabs()
    }

    private fun setUpTabs() {
        val adapter = ViewPagerAdapterForTablayout(supportFragmentManager)
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