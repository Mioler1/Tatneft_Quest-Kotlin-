package com.example.tatneftquest.Fragments

import android.content.Context
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.tatneftquest.Interface.ReplaceFragmentHandler
import com.example.tatneftquest.QuestionOneFragment
import com.example.tatneftquest.QuestionThreeFragment
import com.example.tatneftquest.QuestionTwoFragment
import com.example.tatneftquest.TravelPackage.StartGeneralFragment

open class BaseFragment : Fragment() {
    @Nullable
    protected var mFragmentHandler: ReplaceFragmentHandler? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mFragmentHandler = context as ReplaceFragmentHandler
    }

    fun outputData(
        timeTransit: String,
        numberPoint: Int,
        score: Int,
        firstPoint: String,
        lastPoint: String,
    ) {
        val startGeneralFragment = StartGeneralFragment()
        val bundle = Bundle()
        bundle.putString("timeTransit", timeTransit)
        bundle.putInt("numberPoint", numberPoint)
        bundle.putInt("score", score)
        bundle.putString("firstPoint", firstPoint)
        bundle.putString("lastPoint", lastPoint)
        startGeneralFragment.arguments = bundle
        mFragmentHandler?.replace(startGeneralFragment, true)
    }

    fun fillingQuestOne(
        question: String,
        descriptionQuestions: String,
        indication: String,
        answerOne: String,
        answerTwo: String,
        correctAnswer: String,
    ) {
        val questionOneFragment = QuestionOneFragment()
        val bundle = Bundle()
        bundle.putString("question", question)
        bundle.putString("descriptionQuestions", descriptionQuestions)
        bundle.putString("indication", indication)
        bundle.putString("answerOne", answerOne)
        bundle.putString("answerTwo", answerTwo)
        bundle.putString("correctAnswer", correctAnswer)
        questionOneFragment.arguments = bundle
        mFragmentHandler?.replace(questionOneFragment)
    }

    fun afterAnsweringOne(
        question: String,
        descriptionQuestions: String,
        indication: String,
        answerOne: String,
        answerTwo: String,
        correctAnswer: String,
    ) {
        val questionOneFragment = QuestionOneFragment()
        val bundle = Bundle()
        bundle.putString("question", question)
        bundle.putString("descriptionQuestions", descriptionQuestions)
        bundle.putString("indication", indication)
        bundle.putString("answerOne", answerOne)
        bundle.putString("answerTwo", answerTwo)
        bundle.putString("correctAnswer", correctAnswer)
        questionOneFragment.arguments = bundle
    }

    fun fillingQuestTwo(
        question: String,
        descriptionQuestions: String,
        indication: String,
        answerOne: String,
        answerTwo: String,
        correctAnswer: String,
    ) {
        val questionTwoFragment = QuestionTwoFragment()
        val bundle = Bundle()
        bundle.putString("question", question)
        bundle.putString("descriptionQuestions", descriptionQuestions)
        bundle.putString("indication", indication)
        bundle.putString("answerOne", answerOne)
        bundle.putString("answerTwo", answerTwo)
        bundle.putString("correctAnswer", correctAnswer)
        questionTwoFragment.arguments = bundle
        mFragmentHandler?.replace(questionTwoFragment)
    }

    fun fillingQuestThree(
        question: String,
        descriptionQuestions: String,
        indication: String,
        answerOne: String,
        answerTwo: String,
        correctAnswer: String,
    ) {
        val questionThreeFragment = QuestionThreeFragment()
        val bundle = Bundle()
        bundle.putString("question", question)
        bundle.putString("descriptionQuestions", descriptionQuestions)
        bundle.putString("indication", indication)
        bundle.putString("answerOne", answerOne)
        bundle.putString("answerTwo", answerTwo)
        bundle.putString("correctAnswer", correctAnswer)
        questionThreeFragment.arguments = bundle
        mFragmentHandler?.replace(questionThreeFragment)
    }
}