package com.example.tatneftquest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.tatneftquest.Menu.AppDrawer
import com.example.tatneftquest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityMainBinding
    lateinit var mToolbar: Toolbar
    private lateinit var appDrawer: AppDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        init()
        appDrawer.drawerMenuFunc()
    }

    private fun init() {
        mToolbar = mBinding.toolbar
        appDrawer = AppDrawer(this, mToolbar)
    }
}