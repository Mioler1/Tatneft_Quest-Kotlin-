package com.example.tatneft_quest.firstActivity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.tatneft_quest.MainActivity
import com.example.tatneft_quest.Variables.Companion.INTRO_OPEN
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_PROGRAM
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_EMAIL
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_PASSWORD
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_TOKEN
import com.example.tatneft_quest.slider.SliderActivity
import com.example.tatneft_quest.databinding.ActivityAuthorizationBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AuthorizationActivity : AppCompatActivity() {
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var textInputEmail: TextInputLayout
    private lateinit var textInputPassword: TextInputLayout
    private lateinit var sharedPreferencesUser: SharedPreferences
    private lateinit var sharedPreferencesProgram: SharedPreferences
    private lateinit var binding: ActivityAuthorizationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

        binding.changePassword.setOnClickListener {
            startActivity(Intent(this, RestorePasswordActivity::class.java))
        }
        binding.regButton.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
        binding.comeButton.setOnClickListener { view ->
            authorization(view)
        }
    }

    private fun init() {
        email = binding.emailAuthorization
        password = binding.passwordAuthorization
        textInputEmail = binding.textInputEmail
        textInputPassword = binding.textInputPassword
        sharedPreferencesUser = getSharedPreferences(SAVE_DATA_USER, MODE_PRIVATE)
        sharedPreferencesProgram = getSharedPreferences(SAVE_DATA_PROGRAM, MODE_PRIVATE)

        if (sharedPreferencesUser.getString(SAVE_DATA_USER_TOKEN, "") == "true") {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        if (intent != null) {
            email.setText(intent.getStringExtra("email"))
            password.setText(intent.getStringExtra("password"))
        }
    }

    private fun authorization(view: View) {
        val emailText = email.text.toString()
        val passwordText = password.text.toString()
        clearTextInputLayout()
        if (emailText.isEmpty()) {
            binding.textInputEmail.error = "Пустое поле"
            return
        }
        if (passwordText.isEmpty()) {
            binding.textInputPassword.error = "Пустое поле"
            return
        }
        if (!sharedPreferencesUser.contains((SAVE_DATA_USER_EMAIL))) {
            binding.textInputEmail.error = "У вас нет Email"
            return
        }
        if (!sharedPreferencesUser.contains((SAVE_DATA_USER_PASSWORD))) {
            binding.textInputPassword.error = "У вас нет пароля"
            return
        }
        val sharedEmail = sharedPreferencesUser.getString(SAVE_DATA_USER_EMAIL, "")
        val sharedPassword = sharedPreferencesUser.getString(SAVE_DATA_USER_PASSWORD, "")
        if (emailText == sharedEmail && passwordText == sharedPassword) {
            if (sharedPreferencesProgram.contains(INTRO_OPEN).toString() == "true") {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, SliderActivity::class.java))
                sharedPreferencesProgram.edit().putString(INTRO_OPEN, "true").apply()
            }
            sharedPreferencesUser.edit().putString(SAVE_DATA_USER_TOKEN, "true").apply()
            finish()
        } else {
            Snackbar.make(view, "Неправильный логин или пароль", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun clearTextInputLayout() {
        textInputEmail.error = null
        textInputPassword.error = null
    }
}