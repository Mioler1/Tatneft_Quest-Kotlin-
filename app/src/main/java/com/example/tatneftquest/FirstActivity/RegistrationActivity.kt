package com.example.tatneftquest.FirstActivity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.tatneftquest.R
import com.example.tatneftquest.databinding.ActivityRegistrationBinding
import com.example.tatneftquest.Variables.Companion.IMAGE_PICK_CODE
import com.example.tatneftquest.Variables.Companion.PERMISSION_CODE


class RegistrationActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationBinding
    var check = false
    val passwordCheck =
        """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#${'$'}%!\-_?&])(?=\S+${'$'}).{8,}""".toRegex()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = listOf("Мужской", "Женский")
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, items)

        binding.gender.setAdapter(adapter)

        binding.authButton.setOnClickListener{
            val intent = Intent(this, AuthorizationActivity::class.java)
            startActivity(intent)
        }

        binding.avatar.setOnClickListener{
            pickImageFromGallery()
        }

        binding.regButton.setOnClickListener {
            val emailText = binding.emailRegistration.text.toString()
            val passwordText = binding.passwordRegistration.text.toString()
            val repeatPassword = binding.repeatPasswordRegistration.text.toString()
            val surnameText = binding.surname.text.toString()
            val nameText = binding.name.text.toString()
            val patronymicText = binding.patronymic.text.toString()
            val ageText = binding.age.text.toString()
            val genderText = binding.gender.text.toString()
            val cityText = binding.city.text.toString()
            val numberText = binding.numberRegistration.text.toString()
            val loginText = binding.login.text.toString()
            if (surnameText.isEmpty()) {
                binding.textInputSurname.error = "Пустое поле"
                return@setOnClickListener
            }
            if (nameText.isEmpty()) {
                binding.textInputName.error = "Пустое поле"
                binding.textInputSurname.error = null
                return@setOnClickListener
            }
            if (patronymicText.isEmpty()) {
                binding.textInputPatronymic.error = "Пустое поле"
                binding.textInputName.error = null
                return@setOnClickListener
            }
            if (ageText.isEmpty()) {
                binding.textInputAge.error = "Пустое поле"
                binding.textInputPatronymic.error = null
                return@setOnClickListener
            }
            if (genderText.isEmpty()) {
                binding.textInputGender.error = "Пустое поле"
                binding.textInputAge.error = null
                return@setOnClickListener
            }
            if (cityText.isEmpty()) {
                binding.textInputCity.error = "Пустое поле"
                binding.textInputGender.error = null
                return@setOnClickListener
            }
            if (emailText.isEmpty()) {
                binding.textInputEmail.error = "Пустое поле"
                binding.textInputCity.error = null
                return@setOnClickListener
            }
            if (emailText.length < 10) {
                binding.textInputEmail.error = "Номер телефона введен неверно"
                binding.textInputCity.error = null
                return@setOnClickListener
            }
            if (numberText.isEmpty()) {
                binding.textInputNumber.error = "Пустое поле"
                binding.textInputEmail.error = null
                return@setOnClickListener
            }
            if (loginText.isEmpty()) {
                binding.textInputLogin.error = "Пустое поле"
                binding.textInputNumber.error = null
                return@setOnClickListener
            }
            if (passwordText.isEmpty()) {
                binding.textInputPassword.error = "Пустое поле"
                binding.textInputLogin.error = null
                return@setOnClickListener
            }
            if (passwordText.length < 6) {
                binding.textInputPassword.error = "Минимум 6 символов"
                binding.textInputLogin.error = null
                return@setOnClickListener
            }
            if (passwordText.matches(passwordCheck)) {
                check = true
            }
            if (!check) {
                binding.textInputPassword.error = "Заглавная буква, цифра, спец. символ"
                return@setOnClickListener
            }
            if (repeatPassword.isEmpty()) {
                binding.textInputRepeatPassword.error = "Пустое поле"
                binding.textInputPassword.error = null
                return@setOnClickListener
            }
            if (passwordText != repeatPassword) {
                binding.textInputRepeatPassword.error = "Пароль не совпадают"
                return@setOnClickListener
            }
            startActivity(intent)
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        when(requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                        pickImageFromGallery()
                }
                else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
           binding.avatar.setImageURI(data?.data)
       }
    }
}