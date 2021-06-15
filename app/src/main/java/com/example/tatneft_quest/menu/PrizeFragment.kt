package com.example.tatneft_quest.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.tatneft_quest.databinding.FragmentPrizeBinding

class PrizeFragment : Fragment() {
    private lateinit var binding: FragmentPrizeBinding
    private lateinit var radioButton1: RadioButton
    private lateinit var radioButton2: RadioButton
    private lateinit var radioButton3: RadioButton
    private lateinit var radioButton4: RadioButton
    private lateinit var radioButton5: RadioButton
    private lateinit var radioButton6: RadioButton
    private lateinit var imageClickOne: ImageButton
    private lateinit var imageClickTwo: ImageButton
    private lateinit var imageClickThree: ImageButton
    private lateinit var imageClickFour: ImageButton
    private lateinit var imageClickFive: ImageButton
    private lateinit var imageClickSix: ImageButton

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

        init()

        imageClickOne.setOnClickListener {
            radioButton1.isChecked = true
            radioButton2.isChecked = false
            radioButton3.isChecked = false
            radioButton4.isChecked = false
            radioButton5.isChecked = false
            radioButton6.isChecked = false
        }

        imageClickTwo.setOnClickListener {
            radioButton1.isChecked = false
            radioButton2.isChecked = true
            radioButton3.isChecked = false
            radioButton4.isChecked = false
            radioButton5.isChecked = false
            radioButton6.isChecked = false
        }

        imageClickThree.setOnClickListener {
            radioButton1.isChecked = false
            radioButton2.isChecked = false
            radioButton3.isChecked = true
            radioButton4.isChecked = false
            radioButton5.isChecked = false
            radioButton6.isChecked = false
        }

        imageClickFour.setOnClickListener {
            radioButton1.isChecked = false
            radioButton2.isChecked = false
            radioButton3.isChecked = false
            radioButton4.isChecked = true
            radioButton5.isChecked = false
            radioButton6.isChecked = false
        }

        imageClickFive.setOnClickListener {
            radioButton1.isChecked = false
            radioButton2.isChecked = true
            radioButton3.isChecked = false
            radioButton4.isChecked = false
            radioButton5.isChecked = true
            radioButton6.isChecked = false
        }

        imageClickSix.setOnClickListener {
            radioButton1.isChecked = false
            radioButton2.isChecked = false
            radioButton3.isChecked = false
            radioButton4.isChecked = false
            radioButton5.isChecked = false
            radioButton6.isChecked = true
        }
    }

    private fun init() {
        radioButton1 = binding.radioButton1
        radioButton2 = binding.radioButton2
        radioButton3 = binding.radioButton3
        radioButton4 = binding.radioButton4
        radioButton5 = binding.radioButton5
        radioButton6 = binding.radioButton6
        imageClickOne = binding.imageClickOne
        imageClickTwo = binding.imageClickTwo
        imageClickThree = binding.imageClickThree
        imageClickFour = binding.imageClickFour
        imageClickFive = binding.imageClickFive
        imageClickSix = binding.imageClickSix
    }
}