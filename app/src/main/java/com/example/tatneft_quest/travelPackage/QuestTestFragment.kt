package com.example.tatneft_quest.travelPackage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.tatneft_quest.databinding.FragmentQuestTestBinding
import com.example.tatneft_quest.libs.ImprovedPreference

class QuestTestFragment : Fragment() {
    private lateinit var pointLocation: TextView
    private lateinit var questionNumber: TextView
    private lateinit var questionText: TextView
    private lateinit var answerOne: Button
    private lateinit var answerTwo: Button
    private lateinit var answerThree: Button
    private lateinit var reply: Button
    private lateinit var nextQuestion: ImageButton

    private lateinit var improvedPreference: ImprovedPreference
    private lateinit var binding: FragmentQuestTestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentQuestTestBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        downloadData()
    }

    private fun init() {
        pointLocation = binding.pointLocation
        questionNumber = binding.questionNumber
        questionText = binding.questionText
        answerOne = binding.answerOne
        answerTwo = binding.answerTwo
        answerThree = binding.answerThree
        reply = binding.reply
        nextQuestion = binding.nextQuestion
        improvedPreference = ImprovedPreference(context)
    }

    private fun downloadData() {

    }
}