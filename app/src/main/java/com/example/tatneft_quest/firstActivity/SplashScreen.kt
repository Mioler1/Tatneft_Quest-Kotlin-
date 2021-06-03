package com.example.tatneft_quest.firstActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.tatneft_quest.R

class SplashScreen : AppCompatActivity() {

    private lateinit var ivLogo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        ivLogo = findViewById(R.id.iv_logo)
        ivLogo.alpha = 0f
        ivLogo.animate().setDuration(1500).alpha(10f).withEndAction {
            startActivity(Intent(this, AuthorizationActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
}