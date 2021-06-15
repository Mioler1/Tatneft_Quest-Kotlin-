package com.example.tatneft_quest.travelPackage

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tatneft_quest.Variables.Companion.ACTIVE_TEST
import com.example.tatneft_quest.Variables.Companion.TAG
import com.example.tatneft_quest.Variables.Companion.fragmentList
import com.example.tatneft_quest.Variables.Companion.pointsSheet
import com.example.tatneft_quest.baseClasses.BaseFragment
import com.example.tatneft_quest.databinding.FragmentLocationHistoryBinding
import com.example.tatneft_quest.libs.ImprovedPreference
import com.example.tatneft_quest.services.LocationService

class LocationHistoryFragment : BaseFragment() {
    private lateinit var nameLocation: TextView
    private lateinit var imageLocation: ImageView
    private lateinit var informationLocation: TextView
    private lateinit var testing: Button

    private lateinit var improvedPreference: ImprovedPreference
    private lateinit var binding: FragmentLocationHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLocationHistoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Квест"
        init()
        downloadData()
        testing.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            fragmentList.removeAt(fragmentList.size - 1)
            mFragmentHandler?.replace(QuestTestFragment(), true)
            improvedPreference.putBoolean(ACTIVE_TEST, true)
        }
    }

    private fun init() {
        nameLocation = binding.nameLocation
        imageLocation = binding.imageLocation
        informationLocation = binding.informationLocation
        improvedPreference = ImprovedPreference(context)
        testing = binding.testing
    }

    private fun downloadData() {
        pointsSheet.forEach { el ->
            if (el.getActive()) {
                nameLocation.text = el.title
                imageLocation.setImageResource(el.getIconPicture())
                informationLocation.text = el.getInformation()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        startLocationService()
    }

    private fun startLocationService() {
        if (!isLocationServiceRunning()) {
            val intent = Intent(context, LocationService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ContextCompat.startForegroundService(requireContext(), intent)
            } else {
                activity?.startService(intent)
            }
        }
    }

    @Suppress("DEPRECATION")
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