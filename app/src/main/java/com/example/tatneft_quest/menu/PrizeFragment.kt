package com.example.tatneft_quest.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.tatneft_quest.databinding.FragmentPrizeBinding


class PrizeFragment : Fragment() {

    private lateinit var binding: FragmentPrizeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPrizeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Призы"

        binding.imageClickOne.setOnClickListener {
            binding.radioButton1.isChecked = true
            binding.radioButton2.isChecked = false
            binding.radioButton3.isChecked = false
            binding.radioButton4.isChecked = false
            binding.radioButton5.isChecked = false
            binding.radioButton6.isChecked = false
        }

        binding.imageClickTwo.setOnClickListener {
            binding.radioButton1.isChecked = false
            binding.radioButton2.isChecked = true
            binding.radioButton3.isChecked = false
            binding.radioButton4.isChecked = false
            binding.radioButton5.isChecked = false
            binding.radioButton6.isChecked = false
        }

        binding.imageClickThree.setOnClickListener {
            binding.radioButton1.isChecked = false
            binding.radioButton2.isChecked = false
            binding.radioButton3.isChecked = true
            binding.radioButton4.isChecked = false
            binding.radioButton5.isChecked = false
            binding.radioButton6.isChecked = false
        }

        binding.imageClickFour.setOnClickListener {
            binding.radioButton1.isChecked = false
            binding.radioButton2.isChecked = false
            binding.radioButton3.isChecked = false
            binding.radioButton4.isChecked = true
            binding.radioButton5.isChecked = false
            binding.radioButton6.isChecked = false
        }

        binding.imageClickFive.setOnClickListener {
            binding.radioButton1.isChecked = false
            binding.radioButton2.isChecked = true
            binding.radioButton3.isChecked = false
            binding.radioButton4.isChecked = false
            binding.radioButton5.isChecked = true
            binding.radioButton6.isChecked = false
        }

        binding.imageClickSix.setOnClickListener {
            binding.radioButton1.isChecked = false
            binding.radioButton2.isChecked = false
            binding.radioButton3.isChecked = false
            binding.radioButton4.isChecked = false
            binding.radioButton5.isChecked = false
            binding.radioButton6.isChecked = true
        }
    }
}