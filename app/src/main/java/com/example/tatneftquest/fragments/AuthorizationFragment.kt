package com.example.tatneftquest.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.tatneftquest.MainActivity
import com.example.tatneftquest.R
import com.example.tatneftquest.Slider.SliderActivity
import com.example.tatneftquest.databinding.FragmentAuthorizationBinding


class AuthorizationFragment : Fragment() {
    private lateinit var binding: FragmentAuthorizationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthorizationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.comeButton.setOnClickListener {
            startActivity(Intent(context, SliderActivity::class.java))
        }

    }
    

}