package com.example.lvl1final.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lvl1final.R
import com.example.lvl1final.data.api.CountryDto
import com.example.lvl1final.data.api.GenreDto
import com.example.lvl1final.databinding.FragmentCountryGenreFilterBinding
import com.example.lvl1final.presentation.Arguments
import com.example.lvl1final.presentation.DataStoreViewModel
import com.example.lvl1final.presentation.MainViewModel
import kotlinx.coroutines.launch

class CountryGenreFilterFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private val dataStoreViewModel: DataStoreViewModel by activityViewModels()
    private var _binding: FragmentCountryGenreFilterBinding? = null
    private val binding get() = _binding!!
    private val countryListAdapter =
        CountryListAdapter { countryDto -> setCountryTempValues(countryDto) }
    private val genreListAdapter = GenreListAdapter { genreDto -> setGenreTempValues(genreDto) }
    private var listType: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryGenreFilterBinding.inflate(layoutInflater, container, false)
        listType = arguments?.getString(Arguments.ARG_LIST_TYPE)!!
        if (listType == Arguments.ARG_COUNTRY_LIST) {
            binding.textViewFilterName.text = getString(R.string.country)
            binding.edSearchCountryGenre.hint = getString(R.string.enter_country)
            binding.recyclerViewCountriesOrGenres.adapter = countryListAdapter
        } else {
            binding.textViewFilterName.text = getString(R.string.genre)
            binding.edSearchCountryGenre.hint = getString(R.string.enter_genre)
            binding.recyclerViewCountriesOrGenres.adapter = genreListAdapter
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var countryList: List<CountryDto> = emptyList()
        var genreList: List<GenreDto> = emptyList()
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.countriesAndGenres.collect { data ->
                    if (data != null) {
                        if (listType == Arguments.ARG_COUNTRY_LIST) {
                            countryList = data.countries.sortedBy { it.country }
                            countryListAdapter.submitList(countryList)
                        } else {
                            genreList = data.genres.sortedBy { it.genre }
                            genreListAdapter.submitList(genreList)
                        }


                        edSearchCountryGenre.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(
                                p0: CharSequence?,
                                p1: Int,
                                p2: Int,
                                p3: Int
                            ) {
                            }

                            override fun onTextChanged(
                                s: CharSequence?,
                                start: Int,
                                before: Int,
                                count: Int
                            ) {
                                viewLifecycleOwner.lifecycleScope.launch {
//                                    delay(500)
                                    val query = s.toString()
                                    if (listType == Arguments.ARG_COUNTRY_LIST) {
                                        val filteredList = countryList.filter { countryDto ->
                                            countryDto.country.contains(query, ignoreCase = true)
                                        }
                                        countryListAdapter.submitList(filteredList)
                                    } else {
                                        val filteredList = genreList.filter { genreDto ->
                                            genreDto.genre.contains(query, ignoreCase = true)
                                        }
                                        genreListAdapter.submitList(filteredList)
                                    }
                                }
                            }

                            override fun afterTextChanged(p0: Editable?) {}
                        })


                    }

                }
            }
            imgBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setCountryTempValues(countryDto: CountryDto) {
        dataStoreViewModel.setCountryTempValues(countryDto)
        findNavController().popBackStack()
    }

    private fun setGenreTempValues(genreDto: GenreDto) {
        dataStoreViewModel.setGenreTempValues(genreDto)
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}