package com.example.tatneftquest.FirstActivity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tatneftquest.Slider.SliderActivity
import com.example.tatneftquest.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    var check = false
    val passwordCheck =
        """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#${'$'}%!\-_?&])(?=\S+${'$'}).{8,}""".toRegex()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intent = Intent(context, SliderActivity::class.java)

        binding.regButton.setOnClickListener {
            val numberText = binding.numberRegistration.text.toString()
            val passwordText = binding.passwordRegistration.text.toString()
            val repeatPassword = binding.repeatPasswordRegistration.text.toString()
            if (numberText.isEmpty()) {
                binding.textInputNumber.error = "Пустое поле"
                return@setOnClickListener
            }
            if (passwordText.isEmpty()) {
                binding.textInputPassword.error = "Пустое поле"
                binding.textInputNumber.error = null
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
            if (numberText.length < 10) {
                binding.textInputNumber.error = "Номер телефона введен неверно"
                binding.textInputPassword.error = null
                return@setOnClickListener
            }
            if (passwordText.length < 6) {
                binding.textInputPassword.error = "Минимум 6 символов"
                return@setOnClickListener
            }
            if (passwordText != repeatPassword) {
                binding.textInputRepeatPassword.error = "Пароль не совпадают"
                return@setOnClickListener
            }
            startActivity(intent)
        }
    }

}