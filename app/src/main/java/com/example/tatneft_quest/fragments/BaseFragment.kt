package com.example.tatneft_quest.fragments

import android.content.Context
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.tatneft_quest.Variables.Companion.pointsSheet
import com.example.tatneft_quest.Variables.Companion.testSheet
import com.example.tatneft_quest.`interface`.ReplaceFragmentHandler
import com.example.tatneft_quest.models.ClusterMarkerPoints
import com.example.tatneft_quest.models.TestQuestionsModel
import com.google.android.gms.maps.model.LatLng

open class BaseFragment : Fragment() {
    @Nullable
    protected var mFragmentHandler: ReplaceFragmentHandler? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mFragmentHandler = context as ReplaceFragmentHandler
    }

    fun fillingSheetForPoints(
        id: Int,
        latitude: Double,
        longitude: Double,
        title: String,
        image: Int,
        active: Boolean,
        information: String,
    ) {
        pointsSheet.add(ClusterMarkerPoints(id,
            LatLng(latitude, longitude),
            title,
            image,
            active,
            information))
    }

    fun fillingSheetForTest(
        idPoint: Int,
        numberQuestion: Int,
        textQuestion: String,
        answerOne: String,
        answerTwo: String,
        answerThree: String,
        answerCorrect: String,
    ) {
        testSheet.add(TestQuestionsModel(idPoint,
            numberQuestion,
            textQuestion,
            answerOne,
            answerTwo,
            answerThree,
            answerCorrect))
    }
}