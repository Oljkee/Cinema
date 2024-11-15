package com.example.lvl1final.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lvl1final.domain.models.movieimpl.CountryImpl
import com.example.lvl1final.domain.models.movie.FilterParameters
import com.example.lvl1final.domain.models.movieimpl.GenreImpl
import com.example.lvl1final.domain.usecase.GetOnBoardingCompleteStateUseCase
import com.example.lvl1final.domain.usecase.GetSearchFilterParametersUseCase
import com.example.lvl1final.domain.usecase.UpdateOnBoardingCompleteStateUseCase
import com.example.lvl1final.domain.usecase.UpdateSearchFilterParametersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val getSearchFilterParametersUseCase: GetSearchFilterParametersUseCase,
    private val getOnBoardingCompleteStateUseCase: GetOnBoardingCompleteStateUseCase,
    private val updateOnBoardingCompleteStateUseCase: UpdateOnBoardingCompleteStateUseCase,
    private val updateSearchFilterParametersUseCase: UpdateSearchFilterParametersUseCase
) : ViewModel() {

    val isOnBoardingCompletedFlow = getOnBoardingCompleteStateUseCase.invoke()

    val searchFilterParameters = getSearchFilterParametersUseCase.invoke()

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
            updateOnBoardingCompleteStateUseCase(isCompleted)
        }
    }

    fun updateSearchFilterParameters(filterParameters: FilterParameters) {
        viewModelScope.launch {
            updateSearchFilterParametersUseCase(filterParameters)
        }
    }

    fun setTypeTempValue(type: String) {
        viewModelScope.launch {
            _typeFilterParameterTempValue.value = type
        }
    }

    fun setCountryTempValues(countryImpl: CountryImpl) {
        viewModelScope.launch {
            _countryIdFilterParameterTempValue.value = countryImpl.id!!
            _countryFilterParameterTempValue.value = countryImpl.country
        }
    }

    fun setGenreTempValues(genreImpl: GenreImpl) {
        viewModelScope.launch {
            _genreIdFilterParameterTempValue.value = genreImpl.id!!
            _genreFilterParameterTempValue.value = genreImpl.genre
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
            setCountryTempValues(CountryImpl(country, countryId))
            setGenreTempValues(GenreImpl(genre, genreId))
            setYearTempValues(yearFrom, yearTo)
            setRatingTempValues(ratingFrom, ratingTo)
            setSortingTempValue(sorting)
            setHideWatchedMovieTempStatus(hideWatchedMovies)
        }
    }
}