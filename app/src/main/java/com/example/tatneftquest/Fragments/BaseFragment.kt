package com.example.tatneftquest.Fragments

import android.content.Context
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.tatneftquest.Interface.ReplaceFragmentHandler
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
        mFragmentHandler?.replace(startGeneralFragment)
    }
}