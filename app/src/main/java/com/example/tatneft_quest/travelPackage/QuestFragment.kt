package com.example.tatneft_quest.travelPackage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tatneft_quest.R
import com.example.tatneft_quest.Variables.Companion.ACTIVE_QUEST
import com.example.tatneft_quest.Variables.Companion.LIST_DATA_POINTS
import com.example.tatneft_quest.Variables.Companion.TAG
import com.example.tatneft_quest.Variables.Companion.pointsSheet
import com.example.tatneft_quest.databinding.FragmentQuestBinding
import com.example.tatneft_quest.fragments.BaseFragment
import com.example.tatneft_quest.libs.ImprovedPreference
import com.example.tatneft_quest.models.ClusterMarkerPoints
import kotlin.collections.ArrayList

class QuestFragment : BaseFragment() {
    private lateinit var improvedPreference: ImprovedPreference
    private lateinit var list: ArrayList<ClusterMarkerPoints>
    private lateinit var binding: FragmentQuestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = FragmentQuestBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        binding.city.setOnClickListener {
            if (list.isNotEmpty()) {
                pointsSheet = list
                Log.d(TAG, "not")
            } else {
                Log.d(TAG, "yes")
                fillingSheetForPoints(1, 54.903642, 52.281305,
                    "Парк Шамсинур", R.drawable.icon1, true)
                fillingSheetForPoints(2, 54.897804, 52.266678,
                    "Парк здоровья", R.drawable.icon2, false)
                fillingSheetForPoints(3, 54.904369, 52.287813,
                    "Каскад прудов", R.drawable.icon3, false)
                fillingSheetForPoints(4, 54.898794, 52.29991,
                    "Городской парк", R.drawable.icon4, false)
                improvedPreference.putListObject(LIST_DATA_POINTS, pointsSheet)
            }
            mFragmentHandler?.replace(StartGeneralFragment(), true)
        }
    }

    private fun init() {
        improvedPreference = ImprovedPreference(context)
        list = improvedPreference.getListObject(LIST_DATA_POINTS, ClusterMarkerPoints::class.java)
    }
}