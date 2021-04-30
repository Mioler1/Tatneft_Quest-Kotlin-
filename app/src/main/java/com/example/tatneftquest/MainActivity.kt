package com.example.tatneftquest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.tatneftquest.Menu.AppDrawer
import com.example.tatneftquest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ReplaceFragmentHandler {
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

    override fun replace(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}

