package com.example.tatneftquest.FirstActivity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.tatneftquest.Slider.SliderActivity
import com.example.tatneftquest.databinding.FragmentAuthorizationBinding


class AuthorizationFragment : Fragment() {
    private lateinit var binding: FragmentAuthorizationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAuthorizationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.comeButton.setOnClickListener {
            val numberText = binding.numberAuthorization.text.toString()
            val passwordText = binding.passwordAuthorization.text.toString()
//            if (numberText.isEmpty()) {
//                binding.textInputNumber.error = "Пустое поле"
//                return@setOnClickListener
//            }
//            if (numberText.length < 10) {
//                binding.textInputNumber.error = "Номер телефона введен неверно"
//                return@setOnClickListener
//            }
//            if (passwordText.isEmpty()) {
//                binding.textInputPassword.error = "Пустое поле"
//                binding.textInputNumber.error = null
//                binding.textInputPassword.errorIconDrawable = null
//                return@setOnClickListener
//            }
            val intent = Intent(context, SliderActivity::class.java)
            startActivity(intent)
        }
    }
}