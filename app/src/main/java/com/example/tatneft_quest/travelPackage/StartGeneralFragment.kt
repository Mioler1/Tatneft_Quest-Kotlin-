package com.example.tatneft_quest.travelPackage

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tatneft_quest.R
import com.example.tatneft_quest.Variables
import com.example.tatneft_quest.Variables.Companion.ACTIVE_QUEST
import com.example.tatneft_quest.Variables.Companion.LIST_DATA_POINTS
import com.example.tatneft_quest.Variables.Companion.TAG
import com.example.tatneft_quest.Variables.Companion.TIME
import com.example.tatneft_quest.Variables.Companion.TIME_QUEST
import com.example.tatneft_quest.Variables.Companion.fragmentList
import com.example.tatneft_quest.Variables.Companion.pointsSheet
import com.example.tatneft_quest.databinding.FragmentStartGeneralBinding
import com.example.tatneft_quest.fragments.BaseFragment
import com.example.tatneft_quest.libs.ImprovedPreference
import com.example.tatneft_quest.services.LocationService

class StartGeneralFragment : BaseFragment() {
    private lateinit var timeTransit: TextView
    private lateinit var numberPoint: TextView
    private lateinit var score: TextView
    private lateinit var firstPoint: TextView
    private lateinit var lastPoint: TextView
    private lateinit var stopQuestButton: TextView
    private lateinit var startQuest: Button
    private lateinit var relativeMyTime: RelativeLayout
    private lateinit var myTime: TextView
    private lateinit var improvedPreference: ImprovedPreference

    private var alert: AlertDialog? = null

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

        startQuest.setOnClickListener {
            improvedPreference.putBoolean(ACTIVE_QUEST, true)
            mFragmentHandler?.replace(StartActionFragment(), true)
        }
        binding.seeingMap.setOnClickListener {
            mFragmentHandler?.replace(MapFragment(), true)
        }
        stopQuestButton.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Вы точно хотите прервать квест?")
                .setMessage("Весь ваш текущий результат по данному квесту пропадет")
                .setCancelable(false)
                .setPositiveButton("Да") { _, _ ->
                    alert?.dismiss()
                    improvedPreference.putBoolean(ACTIVE_QUEST, false)
                    improvedPreference.remove(LIST_DATA_POINTS)
                    improvedPreference.remove(TIME_QUEST)
                    pointsSheet.clear()
                    TIME = 0
                    fragmentList.removeAt(fragmentList.size - 1)
                    requireActivity().stopService(Intent(context, LocationService::class.java))
                    super.requireActivity().onBackPressed()
                }
                .setNegativeButton("Нет") { _, _ ->
                    alert?.dismiss()
                }
            alert = builder.create()
            alert!!.show()
        }
    }

    private fun init() {
        timeTransit = binding.timeTransit
        numberPoint = binding.numberPoint
        score = binding.score
        firstPoint = binding.firstPoint
        lastPoint = binding.lastPoint
        startQuest = binding.startQuest
        stopQuestButton = binding.stopQuestButton!!
        relativeMyTime = binding.relativeMyTime!!
        myTime = binding.myTime!!
        improvedPreference = ImprovedPreference(context)

        if (improvedPreference.getBoolean(ACTIVE_QUEST)) {
            startQuest.text = "Продолжить квест"
            stopQuestButton.visibility = View.VISIBLE
            relativeMyTime.visibility = View.VISIBLE
            myTime.text = when {
                isLocationServiceRunning() -> {
                    var sec = (TIME / 1000)
                    var min = sec / 60
                    var hour = min / 60
                    sec %= 60; min %= 60; hour %= 60
                    "$hour час $min минут ${String.format("%02d", sec)} секунд"
                }
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
        }
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

    private fun isLocationServiceRunning(): Boolean {
        val manager = activity?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
        for (service in manager!!.getRunningServices(Int.MAX_VALUE)) {
            if ("com.example.tatneft_quest.services.LocationService" == service.service.className) {
                Log.d(TAG, "isLocationServiceRunning: location service is already running.")
                return true
            }
        }
        Log.d(TAG, "isLocationServiceRunning: location service is not running.")
        return false
    }
}