package com.example.tatneftquest.Menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tatneftquest.Tablayout.DepthPageTransformer
import com.example.tatneftquest.Tablayout.PagerAdapter
import com.example.tatneftquest.TravelPackage.ExcursionFragment
import com.example.tatneftquest.TravelPackage.QuestFragment
import com.example.tatneftquest.databinding.FragmentTravelBinding

class TravelFragment : Fragment() {
    private lateinit var binding: FragmentTravelBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTravelBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Путешествия"
        setupTabLayout()
    }


    private fun setupTabLayout() {
        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager
        val adapter = PagerAdapter(childFragmentManager).also {
            it.addFragment(QuestFragment(), "Квест")
            it.addFragment(ExcursionFragment(), "Экскурсия")
        }
        viewPager.adapter = adapter
        viewPager.setPageTransformer(true, DepthPageTransformer())
        viewPager.isSaveFromParentEnabled = false
        tabLayout.setupWithViewPager(viewPager)
    }

}