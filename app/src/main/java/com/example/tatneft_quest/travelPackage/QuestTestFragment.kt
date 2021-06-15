package com.example.tatneft_quest.travelPackage

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.FragmentManager
import com.example.tatneft_quest.R
import com.example.tatneft_quest.Variables.Companion.ACTIVE_POINT
import com.example.tatneft_quest.Variables.Companion.ACTIVE_QUESTION
import com.example.tatneft_quest.Variables.Companion.ACTIVE_SCAN
import com.example.tatneft_quest.Variables.Companion.ACTIVE_TEST
import com.example.tatneft_quest.Variables.Companion.LIST_DATA_POINTS
import com.example.tatneft_quest.Variables.Companion.NUMBER_POINT
import com.example.tatneft_quest.Variables.Companion.NUMBER_QUESTIONS
import com.example.tatneft_quest.Variables.Companion.QUEST_COMPLETE
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER
import com.example.tatneft_quest.Variables.Companion.SCORE
import com.example.tatneft_quest.Variables.Companion.USER_SCORE
import com.example.tatneft_quest.Variables.Companion.fragmentList
import com.example.tatneft_quest.Variables.Companion.pointsSheet
import com.example.tatneft_quest.Variables.Companion.testSheet
import com.example.tatneft_quest.databinding.FragmentQuestTestBinding
import com.example.tatneft_quest.fragments.BaseFragment
import com.example.tatneft_quest.libs.ImprovedPreference
import com.example.tatneft_quest.menu.TravelFragment
import com.google.android.material.snackbar.Snackbar

class QuestTestFragment : BaseFragment() {
    private lateinit var pointLocation: TextView
    private lateinit var questionNumber: TextView
    private lateinit var questionText: TextView
    private lateinit var answerOneButton: Button
    private lateinit var answerTwoButton: Button
    private lateinit var answerThreeButton: Button
    private lateinit var reply: Button
    private lateinit var nextQuestion: ImageButton

    private lateinit var improvedPreference: ImprovedPreference
    private lateinit var binding: FragmentQuestTestBinding
    private var answerCorrect = ""
    private var answerUser = ""
    private var idActiveElement: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = FragmentQuestTestBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        downloadData()
        clicks()
    }

    private fun init() {
        pointLocation = binding.pointLocation
        questionNumber = binding.questionNumber
        questionText = binding.questionText
        answerOneButton = binding.answerOne
        answerTwoButton = binding.answerTwo
        answerThreeButton = binding.answerThree
        reply = binding.reply
        nextQuestion = binding.nextQuestion
        improvedPreference = ImprovedPreference(context)

        NUMBER_POINT = improvedPreference.getInt(ACTIVE_POINT)

        NUMBER_QUESTIONS = if (improvedPreference.getInt(ACTIVE_QUESTION) != 0)
            improvedPreference.getInt(ACTIVE_QUESTION) else 1
    }

    private fun clicks() {
        answerOneButton.setOnClickListener {
            butOneColor()
            answerTwoButton.setTextColor(getColor(requireContext(), R.color.green))
            answerThreeButton.setTextColor(getColor(requireContext(), R.color.green))
            answerTwoButton.setBackgroundResource(R.drawable.border_button_green)
            answerThreeButton.setBackgroundResource(R.drawable.border_button_green)
            answerUser = answerOneButton.text.toString()
        }

        answerTwoButton.setOnClickListener {
            butTwoColor()
            answerOneButton.setTextColor(getColor(requireContext(), R.color.green))
            answerThreeButton.setTextColor(getColor(requireContext(), R.color.green))
            answerOneButton.setBackgroundResource(R.drawable.border_button_green)
            answerThreeButton.setBackgroundResource(R.drawable.border_button_green)
            answerUser = answerTwoButton.text.toString()
        }

        answerThreeButton.setOnClickListener {
            butThreeColor()
            answerOneButton.setTextColor(getColor(requireContext(), R.color.green))
            answerTwoButton.setTextColor(getColor(requireContext(), R.color.green))
            answerOneButton.setBackgroundResource(R.drawable.border_button_green)
            answerTwoButton.setBackgroundResource(R.drawable.border_button_green)
            answerUser = answerThreeButton.text.toString()
        }

        reply.setOnClickListener { view ->
            if (answerUser == "") {
                Snackbar.make(view, "Нужно выбрать ответ", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            reply.isEnabled = false
            nextQuestion.isEnabled = true
            answerOneButton.isEnabled = false
            answerTwoButton.isEnabled = false
            answerThreeButton.isEnabled = false
            reply.visibility = View.GONE
            nextQuestion.visibility = View.VISIBLE
            NUMBER_QUESTIONS++
            improvedPreference.putInt(ACTIVE_QUESTION, NUMBER_QUESTIONS)
            if (NUMBER_QUESTIONS == 4) {
                pointsSheet.forEach { el ->
                    if (el.getActive()) {
                        idActiveElement = el.getId()
                        el.setActive(false)
                    }
                }
                if (pointsSheet.size > idActiveElement) {
                    NUMBER_POINT++
                    improvedPreference.putInt(ACTIVE_POINT, NUMBER_POINT)
                    pointsSheet[idActiveElement].setActive(true)
                    improvedPreference.putListObjectMarker(LIST_DATA_POINTS, pointsSheet)
                    NUMBER_QUESTIONS = 1
                    improvedPreference.putInt(ACTIVE_QUESTION, NUMBER_QUESTIONS)
                    improvedPreference.putBoolean(ACTIVE_TEST, false)
                    improvedPreference.putBoolean(ACTIVE_SCAN, false)
                }
                if (pointsSheet.size == idActiveElement) {
                    NUMBER_POINT++
                }
                if (pointsSheet.size == NUMBER_POINT) {
                    improvedPreference.putBoolean(QUEST_COMPLETE, true)
                }
            }
            if (answerUser == answerCorrect) {
                SCORE += 2
                requireActivity().getSharedPreferences(SAVE_DATA_USER, MODE_PRIVATE).edit()
                    .putInt(USER_SCORE, SCORE).apply()
            } else {
                val getTextOne = answerOneButton.text
                val getTextTwo = answerTwoButton.text
                val getTextThree = answerThreeButton.text
                if (getTextOne == answerCorrect) {
                    butOneColor()
                } else if (getTextOne == answerUser) {
                    answerOneButton.setBackgroundResource(R.drawable.background_button_red)
                    answerOneButton.setTextColor(Color.WHITE)
                }
                if (getTextTwo == answerCorrect) {
                    butTwoColor()
                } else if (getTextTwo == answerUser) {
                    answerTwoButton.setBackgroundResource(R.drawable.background_button_red)
                    answerTwoButton.setTextColor(Color.WHITE)
                }
                if (getTextThree == answerCorrect) {
                    butThreeColor()
                } else if (getTextThree == answerUser) {
                    answerThreeButton.setBackgroundResource(R.drawable.background_button_red)
                    answerThreeButton.setTextColor(Color.WHITE)
                }
            }
        }

        nextQuestion.setOnClickListener {
            nextQuestion.isEnabled = false
            answerUser = ""
            if (idActiveElement == -1) {
                reply.isEnabled = true
                answerOneButton.isEnabled = true
                answerTwoButton.isEnabled = true
                answerThreeButton.isEnabled = true
                nextQuestion.visibility = View.GONE
                reply.visibility = View.VISIBLE
                downloadData()
                answerOneButton.setTextColor(getColor(requireContext(), R.color.green))
                answerOneButton.setBackgroundResource(R.drawable.border_button_green)
                answerTwoButton.setTextColor(getColor(requireContext(), R.color.green))
                answerTwoButton.setBackgroundResource(R.drawable.border_button_green)
                answerThreeButton.setTextColor(getColor(requireContext(), R.color.green))
                answerThreeButton.setBackgroundResource(R.drawable.border_button_green)
            } else if (idActiveElement != 4) {
                fragmentList.clear()
                fragmentList.add(TravelFragment())
                fragmentList.add(StartGeneralFragment())
                requireActivity().supportFragmentManager.popBackStack("stackFragment",
                    FragmentManager.POP_BACK_STACK_INCLUSIVE)
                mFragmentHandler?.replace(fragmentList[fragmentList.size - 1], true)
                NUMBER_QUESTIONS = 1
                improvedPreference.putInt(ACTIVE_QUESTION, NUMBER_QUESTIONS)
                idActiveElement = -1
            }
            if (improvedPreference.getBoolean(QUEST_COMPLETE)) {
                fragmentList.removeAt(fragmentList.size - 1)
                super.requireActivity().onBackPressed()
            }
        }
    }

    private fun butOneColor() {
        answerOneButton.setBackgroundResource(R.drawable.background_button_green)
        answerOneButton.setTextColor(Color.WHITE)
    }

    private fun butTwoColor() {
        answerTwoButton.setBackgroundResource(R.drawable.background_button_green)
        answerTwoButton.setTextColor(Color.WHITE)
    }

    private fun butThreeColor() {
        answerThreeButton.setBackgroundResource(R.drawable.background_button_green)
        answerThreeButton.setTextColor(Color.WHITE)
    }

    @SuppressLint("SetTextI18n")
    private fun downloadData() {
        if (pointsSheet.isNotEmpty() && testSheet.isNotEmpty()) {
            pointsSheet.forEach { elPoint ->
                if (elPoint.getActive()) {
                    testSheet.forEach { elTest ->
                        if (elPoint.getId() == elTest.getIdPoint()) {
                            if (elTest.getNumberQuestion() == NUMBER_QUESTIONS) {
                                pointLocation.text = "Точка №${elTest.getIdPoint()}"
                                questionNumber.text = "Вопрос №${elTest.getNumberQuestion()}"
                                questionText.text = elTest.getTextQuestion()
                                answerOneButton.text = elTest.getAnswerOne()
                                answerTwoButton.text = elTest.getAnswerTwo()
                                answerThreeButton.text = elTest.getAnswerThree()
                                answerCorrect = elTest.getAnswerCorrect()
                            }
                        }
                    }
                }
            }
        }
    }
}