package com.example.tatneftquest.TravelPackage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.tatneftquest.databinding.FragmentStartGeneralBinding
import com.example.tatneftquest.fragments.BaseFragment
import com.example.tatneftquest.fragments.StartActionFragment

class StartGeneralFragment : BaseFragment() {
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
        val intent = Intent(context, StartActionFragment::class.java)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Квест"
        if (arguments != null) {
            binding.timeTransit.text = arguments?.getString("timeTransit")
            binding.numberPoint.text = arguments?.getInt("numberPoint").toString()
            binding.score.text = arguments?.getInt("score").toString()
            binding.firstPoint.text = arguments?.getString("firstPoint")
            binding.lastPoint.text = arguments?.getString("lastPoint")
        }

        binding.startQuest.setOnClickListener {
            mFragmentHandler?.replace(StartActionFragment())
        }
    }
}