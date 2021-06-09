package com.example.tatneft_quest.fragments

import android.content.Context
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.tatneft_quest.Variables.Companion.pointsSheet
import com.example.tatneft_quest.`interface`.ReplaceFragmentHandler
import com.example.tatneft_quest.models.ClusterMarkerPoints
import com.example.tatneft_quest.travelPackage.testing.QuestionOneFragment
import com.example.tatneft_quest.travelPackage.testing.QuestionThreeFragment
import com.example.tatneft_quest.travelPackage.testing.QuestionTwoFragment
import com.example.tatneft_quest.travelPackage.StartGeneralFragment
import com.google.android.gms.maps.model.LatLng

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

    fun fillingSheetForPoints(
        id: Int,
        latitude: Double,
        longitude: Double,
        title: String,
        image: Int,
        active: Boolean
    ) {
        pointsSheet.add(ClusterMarkerPoints(id, LatLng(latitude, longitude), title, image, active))
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
        mFragmentHandler?.replace(questionOneFragment, false)
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
        mFragmentHandler?.replace(questionTwoFragment, false)
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
        mFragmentHandler?.replace(questionThreeFragment, false)
    }
}