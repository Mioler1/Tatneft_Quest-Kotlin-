package com.example.tatneft_quest.baseClasses

import android.app.Activity
import android.app.Application
import android.os.Bundle

class MyApplication : Application(), Application.ActivityLifecycleCallbacks {
    private var activityCount = 0

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
    }

    fun isAppForeground(): Boolean {
        return activityCount > 0
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {
        activityCount++
    }

    override fun onActivityPaused(activity: Activity) {
        activityCount--
    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}