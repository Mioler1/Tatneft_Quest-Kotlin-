package com.example.tatneft_quest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.tatneft_quest.`interface`.ReplaceFragmentHandler
import com.example.tatneft_quest.menu.AppDrawer
import com.example.tatneft_quest.menu.TravelFragment
import com.example.tatneft_quest.travelPackage.StartActionFragment
import com.example.tatneft_quest.Variables.Companion.fragmentList
import com.example.tatneft_quest.Variables.Companion.menuList
import com.example.tatneft_quest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ReplaceFragmentHandler {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mToolbar: Toolbar
    private lateinit var appDrawer: AppDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        if (menuList.isNotEmpty()) {
            replace(menuList[menuList.size - 1], false)
        } else {
            replace(fragment = if (fragmentList.isNotEmpty()) {
                fragmentList[fragmentList.size - 1]
            } else {
                TravelFragment()
            }, false)
        }
        init()
        appDrawer.drawerMenuFunc()
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
        } else {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                .commit()
        }
    }


    override fun onBackPressed() {
        if (fragmentList.isEmpty()) {
            super.onBackPressed()
        } else {
            if (menuList.isNotEmpty()) {
                if (fragmentList.isNotEmpty()) {
                    if (fragmentList[fragmentList.size - 1] != menuList[menuList.size - 1]) {
                        replace(fragmentList[fragmentList.size - 1], false)
                        appDrawer.setSelection()
                    } else {
                        super.onBackPressed()
                        fragmentList.removeAt(fragmentList.size - 1)
                    }
                }
                menuList.clear()
            } else {
                if (fragmentList.isNotEmpty()) {
                    fragmentList.removeAt(fragmentList.size - 1)
                    super.onBackPressed()
                }
            }
        }
    }

    private fun checkIntent() {
        if (intent.action == "start") {
            replace(StartActionFragment(), false)
        }
    }
}

