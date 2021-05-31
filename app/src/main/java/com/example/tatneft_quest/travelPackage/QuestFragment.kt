package com.example.tatneft_quest.travelPackage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tatneft_quest.databinding.FragmentQuestBinding
import com.example.tatneft_quest.fragments.BaseFragment

class QuestFragment : BaseFragment() {
    private lateinit var binding: FragmentQuestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = FragmentQuestBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.company.setOnClickListener {
            outputData("2 часа", 10, 25, "Татнефть", "Шамсинур")
        }
        binding.city.setOnClickListener{
            outputData("4 часа", 20, 50, "Каскад", "Шамсинур")
        }
    }
}