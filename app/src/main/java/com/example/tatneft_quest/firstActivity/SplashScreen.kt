package com.example.tatneft_quest.firstActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tatneft_quest.R

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_SplashTheme)
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, AuthorizationActivity::class.java))
        finish()
    }
}