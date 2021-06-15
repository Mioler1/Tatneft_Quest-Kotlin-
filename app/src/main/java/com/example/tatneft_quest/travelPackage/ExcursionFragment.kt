package com.example.tatneft_quest.travelPackage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tatneft_quest.R
import com.example.tatneft_quest.baseClasses.BaseFragment

class ExcursionFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_excursion, container, false)
    }
}