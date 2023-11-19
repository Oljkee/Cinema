package com.example.lvl1final.presentation.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lvl1final.R
import com.example.lvl1final.databinding.FragmentSeasonsAndEpisodesBinding
import com.example.lvl1final.presentation.MainViewModel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SeasonsAndEpisodesFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentSeasonsAndEpisodesBinding? = null
    private val binding get() = _binding!!
    private val episodesListAdapter = EpisodesListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeasonsAndEpisodesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            imgBackButton.setOnClickListener { findNavController().popBackStack() }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.movieInfo.collectLatest { movieInfo ->
                    movieInfo?.apply {
                        binding.textViewSerialName.text = nameRu ?: (nameOriginal ?: nameEn)
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.seasonsAndEpisodes.collectLatest { seriesData ->
                    if (seriesData != null) {
                        viewLifecycleOwner.lifecycleScope.launch {
                            if (seriesData.total > 0) {
                                val firstSeason = seriesData.items[0]
                                recyclerViewEpisodes.adapter = episodesListAdapter
                                episodesListAdapter.submitList(firstSeason.episodes)
                                val numberOfEpisodesName =
                                    getNumberOfEpisodesName(firstSeason.episodes.size)
                                textViewNumberOfEpisodes.text = resources.getString(
                                    R.string.season_and_episodes,
                                    firstSeason.number,
                                    numberOfEpisodesName
                                )
                            }
                        }
                        for (i in 0 until seriesData.total) {
                            val season = seriesData.items[i]

                            if (season.episodes.isNotEmpty()) {
                                val chip = Chip(context)
                                chip.text = season.number.toString()
                                chip.setOnClickListener {
                                    recyclerViewEpisodes.adapter = episodesListAdapter
                                    episodesListAdapter.submitList(season.episodes)
                                    val numberOfEpisodesName =
                                        getNumberOfEpisodesName(season.episodes.size)
                                    textViewNumberOfEpisodes.text = resources.getString(
                                        R.string.season_and_episodes,
                                        season.number,
                                        numberOfEpisodesName
                                    )

                                }
                                chipGroupSeasons.addView(chip)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getNumberOfEpisodesName(size: Int): String {
        val episodeArray = resources.getStringArray(R.array.number_of_episodes)
        val numberOfEpisodes = size % 10
        return if ((size in 11..20) || (numberOfEpisodes == 0) || (numberOfEpisodes in 5..9)) {
            "$size ${episodeArray[2]}"
        } else if (numberOfEpisodes == 1) {
            "$size ${episodeArray[0]}"
        } else "$size ${episodeArray[1]}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}