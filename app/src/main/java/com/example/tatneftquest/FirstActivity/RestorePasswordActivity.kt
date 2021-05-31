package com.example.tatneftquest.FirstActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tatneftquest.R
import com.example.tatneftquest.databinding.ActivityRegistrationBinding
import com.example.tatneftquest.databinding.ActivityRestorePasswordBinding

class RestorePasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityRestorePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestorePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backAuthorization.setOnClickListener {
            val intent = Intent(this, AuthorizationActivity::class.java)
            startActivity(intent)
        }
    }

}