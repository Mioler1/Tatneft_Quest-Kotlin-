package com.example.tatneft_quest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.tatneft_quest.Variables.Companion.TAG
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
        if (menuList.isEmpty() && fragmentList.isEmpty()) {
            replace(TravelFragment(), false)
        } else {
            if (menuList.isNotEmpty()) {
                supportFragmentManager.beginTransaction().attach(menuList[menuList.size - 1])
            } else {
                if (supportFragmentManager.findFragmentByTag("fragment") == null) {
                    replace(TravelFragment(), false)
                } else {
                    val fragment = if (fragmentList.isNotEmpty()) {
                        fragmentList[fragmentList.size - 1]
                    } else {
                        TravelFragment()
                    }
                    supportFragmentManager.beginTransaction().attach(fragment)
                }
            }
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
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, "fragment")
                .addToBackStack("stackFragment").commit()
            fragmentList.add(fragment)
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, "fragment").commit()
        }
    }

    override fun onBackPressed() {
        if (fragmentList.isEmpty() && menuList.isEmpty()) {
            super.onBackPressed()
        } else {
            if (menuList.isNotEmpty()) {
                if (fragmentList.isNotEmpty()) {
                    if (supportFragmentManager.findFragmentByTag("fragment") == null) {
                        if (fragmentList[fragmentList.size - 1] != menuList[menuList.size - 1]) {
                            replace(fragmentList[fragmentList.size - 1], false)
                        } else {
                            super.onBackPressed()
                            fragmentList.removeAt(fragmentList.size - 1)
                        }
                    } else {
                        val fragment = if (fragmentList.isNotEmpty()) {
                            fragmentList[fragmentList.size - 1]
                        } else {
                            TravelFragment()
                        }
                        supportFragmentManager.beginTransaction().attach(fragment)
                    }
                } else {
                    replace(TravelFragment(), false)
                }
                appDrawer.setSelection()
                menuList.clear()
            } else {
                if (fragmentList.isNotEmpty()) {
                    fragmentList.removeAt(fragmentList.size - 1)
                    super.onBackPressed()
                } else {
                    super.onBackPressed()
                }
            }
        }
    }

    private fun checkIntent() {
        if (intent.action == "start") {
            menuList.clear()
            replace(StartActionFragment(), false)
        }
    }

}

