package com.example.tatneftquest.fragments

import android.content.Context
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.tatneftquest.Interface.ReplaceFragmentHandler


abstract class BaseFragment : Fragment() {
    @Nullable
    protected var mFragmentHandler: ReplaceFragmentHandler? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mFragmentHandler = context as ReplaceFragmentHandler
    }
}