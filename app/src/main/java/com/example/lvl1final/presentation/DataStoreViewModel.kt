package com.example.lvl1final.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lvl1final.data.api.CountryDto
import com.example.lvl1final.data.api.GenreDto
import com.example.lvl1final.data.entity.FilterParameters
import com.example.lvl1final.presentation.onboarding.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val isOnBoardingCompletedFlow = userPreferencesRepository.isOnBoardingCompletedFlow

    val searchFilterParameters = userPreferencesRepository.searchFilterParameters

    private val _typeFilterParameterTempValue = MutableStateFlow("")
    val typeFilterParameterTempValue = _typeFilterParameterTempValue.asStateFlow()


    private val _countryIdFilterParameterTempValue = MutableStateFlow(0)
    val countryIdFilterParameterTempValue = _countryIdFilterParameterTempValue.asStateFlow()

    private val _countryFilterParameterTempValue = MutableStateFlow("")
    val countryFilterParameterTempValue = _countryFilterParameterTempValue.asStateFlow()


    private val _genreIdFilterParameterTempValue = MutableStateFlow(0)
    val genreIdFilterParameterTempValue = _genreIdFilterParameterTempValue.asStateFlow()

    private val _genreFilterParameterTempValue = MutableStateFlow("")
    val genreFilterParameterTempValue = _genreFilterParameterTempValue.asStateFlow()


    private val _yearFromFilterParameterTempValue = MutableStateFlow("")
    val yearFromFilterParameterTempValue = _yearFromFilterParameterTempValue.asStateFlow()

    private val _yearToFilterParameterTempValue = MutableStateFlow("")
    val yearToFilterParameterTempValue = _yearToFilterParameterTempValue.asStateFlow()


    private val _ratingFromFilterParameterTempValue = MutableStateFlow("")
    val ratingFromFilterParameterTempValue = _ratingFromFilterParameterTempValue.asStateFlow()

    private val _ratingToFilterParameterTempValue = MutableStateFlow("")
    val ratingToFilterParameterTempValue = _ratingToFilterParameterTempValue.asStateFlow()


    private val _sortingFilterParameterTempValue = MutableStateFlow("")
    val sortingFilterParameterTempValue = _sortingFilterParameterTempValue.asStateFlow()


    private val _hideWatchedMovieTempValue = MutableStateFlow(true)
    val hideWatchedMovieTempValue = _hideWatchedMovieTempValue.asStateFlow()

    fun updateOnBoardingCompleted(isCompleted: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.updateOnBoardingCompleted(isCompleted)
        }
    }

    fun updateSearchFilterParameters(filterParameters: FilterParameters) {
        viewModelScope.launch {
            userPreferencesRepository.updateSearchFilterParameters(filterParameters)
        }
    }

    fun setTypeTempValue(type: String) {
        viewModelScope.launch {
            _typeFilterParameterTempValue.value = type
        }
    }

    fun setCountryTempValues(countryDto: CountryDto) {
        viewModelScope.launch {
            _countryIdFilterParameterTempValue.value = countryDto.id!!
            _countryFilterParameterTempValue.value = countryDto.country
        }
    }

    fun setGenreTempValues(genreDto: GenreDto) {
        viewModelScope.launch {
            _genreIdFilterParameterTempValue.value = genreDto.id!!
            _genreFilterParameterTempValue.value = genreDto.genre
        }
    }

    fun setYearTempValues(yearFrom: String, yearTo: String) {
        viewModelScope.launch {
            _yearFromFilterParameterTempValue.value = yearFrom
            _yearToFilterParameterTempValue.value = yearTo
        }
    }

    fun setRatingTempValues(ratingFrom: String, ratingTo: String) {
        viewModelScope.launch {
            _ratingFromFilterParameterTempValue.value = ratingFrom
            _ratingToFilterParameterTempValue.value = ratingTo
        }
    }

    fun setSortingTempValue(sorting: String) {
        viewModelScope.launch {
            _sortingFilterParameterTempValue.value = sorting
        }
    }

    fun changeHideWatchedMovieTempStatus() {
        viewModelScope.launch {
            _hideWatchedMovieTempValue.value = !_hideWatchedMovieTempValue.value
        }
    }

    fun setHideWatchedMovieTempStatus(hideWatchedMovie: Boolean) {
        viewModelScope.launch {
            _hideWatchedMovieTempValue.value = hideWatchedMovie
        }
    }

    val yearsFlow = combine(
        _yearFromFilterParameterTempValue,
        _yearToFilterParameterTempValue
    ) { yearFromValue, yearToValue ->
        listOf(yearFromValue, yearToValue)
    }

    fun setAllTempValues(filterParameters: FilterParameters) {
        filterParameters.apply {
            setTypeTempValue(type)
            setCountryTempValues(CountryDto(country, countryId))
            setGenreTempValues(GenreDto(genre, genreId))
            setYearTempValues(yearFrom, yearTo)
            setRatingTempValues(ratingFrom, ratingTo)
            setSortingTempValue(sorting)
            setHideWatchedMovieTempStatus(hideWatchedMovies)
        }
    }
}