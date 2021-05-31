package com.example.tatneftquest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tatneftquest.Fragments.BaseFragment
import com.example.tatneftquest.databinding.FragmentLocationHistoryBinding

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

        binding.testing.setOnClickListener{
            fillingQuestOne("Вопрос 1/3:", "Мы с Вами находится у главного здания ПАО Татнефть. Однако, оперативное управление нефтедобычей осуществялется в нефтегазодобывающих управлениях(НГДУ). А знаете ли вы, сколько НГДУ входит в ПАО Татнефть?","Выберите правильный ответ:", "7", "5", "9")
        }
    }


}