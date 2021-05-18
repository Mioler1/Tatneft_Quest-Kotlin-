package com.example.tatneftquest.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged

import com.example.tatneftquest.MainActivity
import com.example.tatneftquest.Slider.SliderActivity
import com.example.tatneftquest.databinding.FragmentAuthorizationBinding
import java.util.concurrent.Executors


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
        val intent = Intent(context, SliderActivity::class.java)

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
            startActivity(intent)
        }
}
}