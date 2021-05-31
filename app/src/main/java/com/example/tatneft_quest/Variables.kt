package com.example.tatneft_quest

import androidx.fragment.app.Fragment
import kotlin.properties.Delegates

open class Variables {
    companion object{
        const val TAG = "check"
        val fragmentList: ArrayList<Fragment> = ArrayList()
        val menuList: ArrayList<Fragment> = ArrayList()
        var LATITUDE by Delegates.notNull<Double>()
        var LONGTITUDE by Delegates.notNull<Double>()
    }
}