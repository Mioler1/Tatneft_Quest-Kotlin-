package com.example.tatneft_quest.travelPackage

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.tatneft_quest.Variables
import com.example.tatneft_quest.Variables.Companion.NUMBER_POINT
import com.example.tatneft_quest.Variables.Companion.NUMBER_QUESTIONS
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_LOGIN
import com.example.tatneft_quest.Variables.Companion.SCORE
import com.example.tatneft_quest.Variables.Companion.TIME
import com.example.tatneft_quest.Variables.Companion.fragmentList
import com.example.tatneft_quest.Variables.Companion.pointsSheet
import com.example.tatneft_quest.Variables.Companion.testSheet
import com.example.tatneft_quest.databinding.FragmentFinishQuestBinding
import com.example.tatneft_quest.fragments.BaseFragment
import com.example.tatneft_quest.libs.ImprovedPreference
import com.example.tatneft_quest.menu.TravelFragment

class FinishQuestFragment : BaseFragment() {
    private lateinit var compliment: TextView
    private lateinit var timeQuest: TextView
    private lateinit var finishQuest: Button

    private lateinit var improvedPreference: ImprovedPreference
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: FragmentFinishQuestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFinishQuestBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        downloadData()
    }

    private fun init() {
        sharedPreferences = requireActivity().getSharedPreferences(SAVE_DATA_USER, MODE_PRIVATE)
        improvedPreference = ImprovedPreference(context)
        compliment = binding.compliments
        timeQuest = binding.timeQuest
        finishQuest = binding.finishQuest

        finishQuest.setOnClickListener {
            clear()
            fragmentList.clear()
            super.requireActivity().onBackPressed()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun downloadData() {
        var sec = (improvedPreference.getInt(Variables.TIME_QUEST) / 1000)
        var min = sec / 60
        var hour = min / 60
        sec %= 60; min %= 60; hour %= 60

        compliment.text = "Поздравляем с прохождением квеста ${
            sharedPreferences
                .getString(SAVE_DATA_USER_LOGIN, "")
        }"
        timeQuest.text = "$hour час $min минут ${String.format("%02d", sec)} секунд"
    }

    private fun clear() {
        improvedPreference.clear()
        pointsSheet.clear()
        testSheet.clear()
        TIME = 0
        NUMBER_QUESTIONS = 0
        NUMBER_POINT = 0
        SCORE = 0
    }
}