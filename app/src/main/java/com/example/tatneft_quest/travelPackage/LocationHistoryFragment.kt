package com.example.tatneft_quest.travelPackage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.tatneft_quest.Variables.Companion.ACTIVE_TEST
import com.example.tatneft_quest.Variables.Companion.fragmentList
import com.example.tatneft_quest.Variables.Companion.pointsSheet
import com.example.tatneft_quest.fragments.BaseFragment
import com.example.tatneft_quest.databinding.FragmentLocationHistoryBinding
import com.example.tatneft_quest.libs.ImprovedPreference

class LocationHistoryFragment : BaseFragment() {
    private lateinit var nameLocation: TextView
    private lateinit var imageLocation: ImageView
    private lateinit var informationLocation: TextView

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
        init()
        downloadData()
        binding.testing.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            fragmentList.removeAt(fragmentList.size - 1)
            mFragmentHandler?.replace(QuestTestFragment(), true)
            improvedPreference.putBoolean(ACTIVE_TEST, true)
        }
    }

    private fun init() {
        nameLocation = binding.nameLocation
        imageLocation = binding.imageLocation!!
        informationLocation = binding.informationLocation
        improvedPreference = ImprovedPreference(context)
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

}