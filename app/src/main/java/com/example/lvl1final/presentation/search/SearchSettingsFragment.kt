package com.example.lvl1final.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lvl1final.R
import com.example.lvl1final.databinding.FragmentSearchSettingsBinding
import com.example.lvl1final.presentation.Arguments
import com.example.lvl1final.presentation.DataStoreViewModel
import com.example.lvl1final.domain.models.movie.FilterParameters
import com.example.lvl1final.presentation.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SearchSettingsFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private val dataStoreViewModel: DataStoreViewModel by activityViewModels()
    private var _binding: FragmentSearchSettingsBinding? = null
    private val binding get() = _binding!!
    private val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewLifecycleOwner.lifecycleScope.launch {
                        val filterParameters = dataStoreViewModel.searchFilterParameters.first()
                        dataStoreViewModel.setAllTempValues(filterParameters = filterParameters)
                        findNavController().popBackStack()
                    }
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchSettingsBinding.inflate(layoutInflater, container, false)
        viewModel.getCountriesAndGenres()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                dataStoreViewModel.typeFilterParameterTempValue.collectLatest { type ->
                    when (type) {
                        Arguments.ARG_FILM_TYPE -> btnFilterMovies.isChecked = true
                        Arguments.ARG_SERIES_TYPE -> btnFilterSeries.isChecked = true
                        else -> btnFilterAll.isChecked = true
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                dataStoreViewModel.countryFilterParameterTempValue.collectLatest { country ->
                    tvSelectedCountry.text = country
                    if (country == "") {
                        tvSelectedCountry.text = getString(R.string.all)
                    } else {
                        tvSelectedCountry.text = country.replaceFirstChar { it.uppercase() }
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                dataStoreViewModel.genreFilterParameterTempValue.collectLatest { genre ->
                    if (genre == "") {
                        tvSelectedGenre.text = getString(R.string.all)
                    } else {
                        tvSelectedGenre.text = genre.replaceFirstChar { it.uppercase() }
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                dataStoreViewModel.yearsFlow.collectLatest { years ->
                    val yearFromValue = years[0]
                    val yearToValue = years[1]
                    tvSelectedYears.text =
                        getString(R.string.selected_period, yearFromValue, yearToValue)
                }
            }


            val ratingFlow = combine(
                dataStoreViewModel.ratingFromFilterParameterTempValue,
                dataStoreViewModel.ratingToFilterParameterTempValue
            ) { ratingFromValue, ratingToValue ->
                listOf(ratingFromValue, ratingToValue)
            }
            viewLifecycleOwner.lifecycleScope.launch {
                ratingFlow.collectLatest { ratingValues ->
                    val ratingFrom = ratingValues[0]
                    val ratingTo = ratingValues[1]
                    rangeSliderRating.setValues(ratingFrom.toFloat(), ratingTo.toFloat())

                    tvSelectedRating.text =
                        if (ratingFrom.toInt() == 1 && ratingTo.toInt() == 10) {
                            getString(R.string.Any)
                        } else {
                            getString(R.string.selected_rating, ratingFrom, ratingTo)
                        }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                dataStoreViewModel.sortingFilterParameterTempValue.collectLatest { sorting ->
                    when (sorting) {
                        Arguments.SORT_BY_NUM_VOTE_FILTER -> btnSortByPopularity.isChecked =
                            true

                        Arguments.SORT_BY_RATING_FILTER -> btnSortByRating.isChecked =
                            true

                        else -> btnSortByDate.isChecked = true
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                dataStoreViewModel.hideWatchedMovieTempValue.collectLatest { hideWatchedMovies ->
                    if (hideWatchedMovies) {
                        imgMovieWatchedState.setImageResource(R.drawable.ic_eye_closed)
                        tvMovieWatchedState.text = getString(R.string.hide_watched)
                    } else {
                        imgMovieWatchedState.setImageResource(R.drawable.ic_eye)
                        tvMovieWatchedState.text = getString(R.string.display_watched)
                    }
                }
            }


            btnFilterAll.setOnClickListener {
                dataStoreViewModel.setTypeTempValue(Arguments.ARG_ALL_TYPE)
            }

            btnFilterMovies.setOnClickListener {
                dataStoreViewModel.setTypeTempValue(Arguments.ARG_FILM_TYPE)
            }

            btnFilterSeries.setOnClickListener {
                dataStoreViewModel.setTypeTempValue(Arguments.ARG_SERIES_TYPE)
            }

            linearLayoutCountryFilter.setOnClickListener {
                bundle.putString(Arguments.ARG_LIST_TYPE, Arguments.ARG_COUNTRY_LIST)
                findNavController().navigate(
                    R.id.action_searchSettingsFragment_to_countryGenreFilterFragment,
                    bundle
                )
            }

            linearLayoutGenreFilter.setOnClickListener {
                bundle.putString(Arguments.ARG_LIST_TYPE, Arguments.ARG_GENRE_LIST)
                findNavController().navigate(
                    R.id.action_searchSettingsFragment_to_countryGenreFilterFragment,
                    bundle
                )
            }

            linearLayoutYearFilter.setOnClickListener {
                findNavController().navigate(R.id.action_searchSettingsFragment_to_dateFilterFragment)
            }

            rangeSliderRating.addOnChangeListener { slider, _, _ ->
                val min = slider.values[0]
                val max = slider.values[1]
                dataStoreViewModel.setRatingTempValues(
                    min.toInt().toString(),
                    max.toInt().toString()
                )
            }

            btnSortByDate.setOnClickListener {
                dataStoreViewModel.setSortingTempValue(Arguments.SORT_BY_YEAR_FILTER)
            }

            btnSortByPopularity.setOnClickListener {
                dataStoreViewModel.setSortingTempValue(Arguments.SORT_BY_NUM_VOTE_FILTER)
            }

            btnSortByRating.setOnClickListener {
                dataStoreViewModel.setSortingTempValue(Arguments.SORT_BY_RATING_FILTER)
            }

            linearLayoutMovieWatchedStatus.setOnClickListener {
                dataStoreViewModel.changeHideWatchedMovieTempStatus()
            }

            btnApplySettings.setOnClickListener {
                val filterParameters: FilterParameters
                dataStoreViewModel.apply {
                    val countryId = countryIdFilterParameterTempValue.value
                    val genreId = genreIdFilterParameterTempValue.value
                    val country = countryFilterParameterTempValue.value
                    val genre = genreFilterParameterTempValue.value
                    val sorting = sortingFilterParameterTempValue.value
                    val type = typeFilterParameterTempValue.value
                    val ratingFrom = ratingFromFilterParameterTempValue.value
                    val ratingTo = ratingToFilterParameterTempValue.value
                    val yearFrom = yearFromFilterParameterTempValue.value
                    val yearTo = yearToFilterParameterTempValue.value
                    val hideWatchedMovies = hideWatchedMovieTempValue.value

                    filterParameters = FilterParameters(
                        countryId = countryId,
                        genreId = genreId,
                        country = country,
                        genre = genre,
                        sorting = sorting,
                        type = type,
                        ratingFrom = ratingFrom,
                        ratingTo = ratingTo,
                        yearFrom = yearFrom,
                        yearTo = yearTo,
                        hideWatchedMovies = hideWatchedMovies
                    )
                }
                dataStoreViewModel.updateSearchFilterParameters(filterParameters)
                dataStoreViewModel.setAllTempValues(filterParameters = filterParameters)
                viewModel.updateSearchFilterParameters(filterParameters)
                findNavController().navigate(R.id.action_searchSettingsFragment_to_searchMainFragment)

            }

            imgBackButton.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    val filterParameters = dataStoreViewModel.searchFilterParameters.first()
                    dataStoreViewModel.setAllTempValues(filterParameters = filterParameters)
                    findNavController().popBackStack()
                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}