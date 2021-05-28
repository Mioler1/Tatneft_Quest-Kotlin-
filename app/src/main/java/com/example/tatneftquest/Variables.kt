package com.example.tatneftquest

import kotlin.properties.Delegates

open class Variables {
    companion object{
        var LATITUDE by Delegates.notNull<Double>()
        var LONGTITUDE by Delegates.notNull<Double>()
    }
}