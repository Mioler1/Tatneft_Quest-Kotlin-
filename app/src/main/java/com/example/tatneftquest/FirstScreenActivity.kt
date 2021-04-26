package com.example.tatneftquest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.tatneftquest.Slider.SliderActivity

class FirstScreenActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)

        val start = findViewById<Button>(R.id.buttonReg)
        start.setOnClickListener {
            val intent = Intent(this, SliderActivity::class.java)
            startActivity(intent)
        }
    }

}