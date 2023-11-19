package com.example.lvl1final.presentation.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lvl1final.databinding.FragmentOnBoardingItemBinding
import com.example.lvl1final.presentation.Arguments


class OnBoardingItem : Fragment() {
    private var _binding: FragmentOnBoardingItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingItemBinding.inflate(layoutInflater, container, false)
        arguments?.let { argument ->
            _binding?.imgOnboarding?.setImageResource(argument.getInt(Arguments.ARG_IMAGE))
            _binding?.textviewOnboarding?.text = argument.getString(Arguments.ARG_DESCRIPTION)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}