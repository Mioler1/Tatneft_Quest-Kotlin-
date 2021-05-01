package com.example.tatneftquest.TravelPackage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tatneftquest.databinding.FragmentQuestBinding
import com.example.tatneftquest.fragments.BaseFragment

class QuestFragment : BaseFragment() {
    private lateinit var binding: FragmentQuestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textCity.setOnClickListener {
            mFragmentHandler?.replace(StartGeneralFragment())
        }
    }
}