package com.example.tatneftquest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.tatneftquest.Interface.ReplaceFragmentHandler
import com.example.tatneftquest.Menu.AppDrawer
import com.example.tatneftquest.Menu.TravelFragment
import com.example.tatneftquest.databinding.ActivityMainBinding
import com.example.tatneftquest.fragments.AuthorizationFragment

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
        replace(TravelFragment())
    }

    private fun init() {
        mToolbar = mBinding.toolbar
        appDrawer = AppDrawer(this, mToolbar)
    }

    override fun replace(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}

