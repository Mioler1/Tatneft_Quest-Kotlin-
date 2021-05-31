package com.example.tatneft_quest.firstActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tatneft_quest.slider.SliderActivity
import com.example.tatneft_quest.databinding.ActivityAuthorizationBinding

class AuthorizationActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthorizationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.changePassword.setOnClickListener {
            val intent = Intent(this, RestorePasswordActivity::class.java)
            startActivity(intent)
        }

        binding.regButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
        binding.comeButton.setOnClickListener {
            val numberText = binding.emailAuthorization.text.toString()
            val passwordText = binding.passwordAuthorization.text.toString()
//            if (numberText.isEmpty()) {
//                binding.textInputEmail.error = "Пустое поле"
//                return@setOnClickListener
//            }
//            if (numberText.length < 10) {
//                binding.textInputEmail.error = "Номер телефона введен неверно"
//                return@setOnClickListener
//            }
//            if (passwordText.isEmpty()) {
//                binding.textInputPassword.error = "Пустое поле"
//                binding.textInputNumber.error = null
//                binding.textInputPassword.errorIconDrawable = null
//                return@setOnClickListener
//            }
            val intent = Intent(this, SliderActivity::class.java)
            startActivity(intent)
        }
    }

}