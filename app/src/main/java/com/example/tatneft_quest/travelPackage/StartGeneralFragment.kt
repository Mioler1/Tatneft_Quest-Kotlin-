package com.example.tatneft_quest.travelPackage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tatneft_quest.Variables.Companion.pointsSheet
import com.example.tatneft_quest.databinding.FragmentStartGeneralBinding
import com.example.tatneft_quest.fragments.BaseFragment

class StartGeneralFragment : BaseFragment() {
    private lateinit var timeTransit: TextView
    private lateinit var numberPoint: TextView
    private lateinit var score: TextView
    private lateinit var firstPoint: TextView
    private lateinit var lastPoint: TextView

    private lateinit var binding: FragmentStartGeneralBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentStartGeneralBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Квест"
        init()
        downloadData()

        binding.startQuest.setOnClickListener {
            mFragmentHandler?.replace(StartActionFragment(), true)
        }
        binding.seeingMap.setOnClickListener {
            mFragmentHandler?.replace(MapFragment(), true)
        }
    }

    private fun init() {
        timeTransit = binding.timeTransit
        numberPoint = binding.numberPoint
        score = binding.score
        firstPoint = binding.firstPoint
        lastPoint = binding.lastPoint
    }

    @SuppressLint("SetTextI18n")
    private fun downloadData() {
        if (pointsSheet.isNotEmpty()) {
            timeTransit.text = "${pointsSheet.size} часа"
            numberPoint.text = pointsSheet.size.toString()
            score.text = "${pointsSheet.size * 6}"
            firstPoint.text = pointsSheet[0].title
            lastPoint.text = pointsSheet[pointsSheet.size - 1].title
        }
    }
}