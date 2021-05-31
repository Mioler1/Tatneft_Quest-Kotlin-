package com.example.tatneftquest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.tatneftquest.Interface.ReplaceFragmentHandler
import com.example.tatneftquest.Menu.AppDrawer
import com.example.tatneftquest.Menu.TravelFragment
import com.example.tatneftquest.TravelPackage.StartActionFragment
import com.example.tatneftquest.Variables.Companion.TAG
import com.example.tatneftquest.Variables.Companion.fragmentList
import com.example.tatneftquest.Variables.Companion.menuList
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
        replace(TravelFragment(), false)
        checkIntent()
    }

    private fun init() {
        mToolbar = mBinding.toolbar
        appDrawer = AppDrawer(this, mToolbar)
    }

    override fun replace(fragment: Fragment, boolean: Boolean) {
        if (boolean) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                .addToBackStack("stackFragment").commit()
            fragmentList.add(fragment)
            Log.d(TAG, "replace: $fragmentList")
        } else {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onBackPressed() {
        if (fragmentList.isEmpty()) {
            super.onBackPressed()
        }
        if (menuList.isNotEmpty()) {
            menuList.clear()
            replace(fragmentList[fragmentList.size -1], false)
        } else {
            if (fragmentList.isNotEmpty()) {
                fragmentList.removeAt(fragmentList.size - 1)
                super.onBackPressed()
            }
        }
    }

    private fun checkIntent() {
        if (intent.action == "start") {
            replace(StartActionFragment(), false)
        }
    }
}

