package com.example.lvl1final.presentation.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.lvl1final.databinding.FragmentOnboardingSkippedBinding
import com.example.lvl1final.presentation.Arguments
import com.example.lvl1final.presentation.DataStoreViewModel
import kotlinx.coroutines.launch

class OnBoardingToMainTransitionFragment : Fragment() {
    private val dataStoreViewModel: DataStoreViewModel by activityViewModels()
    private var _binding: FragmentOnboardingSkippedBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingSkippedBinding.inflate(layoutInflater, container, false)
        arguments?.let { argument ->
            _binding?.imgOnboarding?.setImageResource(argument.getInt(Arguments.ARG_IMAGE))
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            dataStoreViewModel.updateOnBoardingCompleted(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}