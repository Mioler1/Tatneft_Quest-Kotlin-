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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.tatneft_quest.Variables
import com.example.tatneft_quest.Variables.Companion.NUMBER_POINT
import com.example.tatneft_quest.Variables.Companion.NUMBER_QUESTIONS
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_LOGIN
import com.example.tatneft_quest.Variables.Companion.SCORE
import com.example.tatneft_quest.Variables.Companion.TIME
import com.example.tatneft_quest.Variables.Companion.TIME_QUEST
import com.example.tatneft_quest.Variables.Companion.USER_SCORE
import com.example.tatneft_quest.Variables.Companion.fragmentList
import com.example.tatneft_quest.Variables.Companion.menuList
import com.example.tatneft_quest.Variables.Companion.pointsSheet
import com.example.tatneft_quest.Variables.Companion.testSheet
import com.example.tatneft_quest.databinding.FragmentFinishQuestBinding
import com.example.tatneft_quest.baseClasses.BaseFragment
import com.example.tatneft_quest.libs.ImprovedPreference
import com.example.tatneft_quest.menu.PrizeFragment

class FinishQuestFragment : BaseFragment() {
    private lateinit var compliment: TextView
    private lateinit var timeQuest: TextView
    private lateinit var finishQuest: Button
    private lateinit var description: TextView
    private lateinit var parting: TextView
    private lateinit var score: TextView

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
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Квест"
        init()
        downloadData()
    }

    private fun init() {
        sharedPreferences = requireActivity().getSharedPreferences(SAVE_DATA_USER, MODE_PRIVATE)
        improvedPreference = ImprovedPreference(context)
        compliment = binding.compliments
        timeQuest = binding.timeQuest
        finishQuest = binding.finishQuest
        parting = binding.parting
        description = binding.description
        score = binding.score

        finishQuest.setOnClickListener {
            clear()
            fragmentList.clear()
            requireActivity().supportFragmentManager.popBackStack("stackFragment",
                FragmentManager.POP_BACK_STACK_INCLUSIVE)
            menuList.add(PrizeFragment())
            mFragmentHandler?.replace(PrizeFragment(), false)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun downloadData() {
        compliment.text = "Поздравляем с успешным прохождением квеста \n ${
            sharedPreferences
                .getString(SAVE_DATA_USER_LOGIN, "")
        }"
        description.text = "Обменивайте полученные баллы на призы и бонусы от ПАО Татнефть"
        parting.text = "До скорых встреч!"

        timeQuest.text = when {
            improvedPreference.getInt(TIME_QUEST) != 0 -> {
                var sec = (improvedPreference.getInt(TIME_QUEST) / 1000)
                var min = sec / 60
                var hour = min / 60
                sec %= 60; min %= 60; hour %= 60
                "$hour час $min минут ${String.format("%02d", sec)} секунд"
            }
            else -> {
                "0 часов 0 минут 0 секунд"
            }
        }
        SCORE = if (requireActivity().getSharedPreferences(SAVE_DATA_USER, MODE_PRIVATE)
                .getInt(USER_SCORE, 0) != 0
        ) requireActivity().getSharedPreferences(SAVE_DATA_USER, MODE_PRIVATE)
            .getInt(USER_SCORE, 0) else 0
        score.text = "У вас $SCORE баллов"
    }

    private fun clear() {
        improvedPreference.clear()
        pointsSheet.clear()
        testSheet.clear()
        TIME = 0
        NUMBER_QUESTIONS = 0
        NUMBER_POINT = 0
    }
}