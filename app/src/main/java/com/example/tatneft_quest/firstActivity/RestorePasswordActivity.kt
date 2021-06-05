package com.example.tatneft_quest.firstActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tatneft_quest.databinding.ActivityRestorePasswordBinding

class RestorePasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityRestorePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestorePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backAuthorization.setOnClickListener {
            super.onBackPressed()
        }
    }

}