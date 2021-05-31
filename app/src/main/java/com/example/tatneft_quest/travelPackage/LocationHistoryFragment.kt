package com.example.tatneft_quest.travelPackage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tatneft_quest.fragments.BaseFragment
import com.example.tatneft_quest.databinding.FragmentLocationHistoryBinding

class LocationHistoryFragment : BaseFragment() {

    private lateinit var binding: FragmentLocationHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLocationHistoryBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.testing.setOnClickListener {
            fillingQuestOne("Вопрос 1/3:",
                "Мы с Вами находится у главного здания ПАО Татнефть. Однако, оперативное управление нефтедобычей осуществялется в нефтегазодобывающих управлениях(НГДУ). А знаете ли вы, сколько НГДУ входит в ПАО Татнефть?",
                "Выберите правильный ответ:",
                "7",
                "5",
                "9")
        }
    }

}