package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.movie.FilterParameters
import com.example.lvl1final.presentation.Arguments
import javax.inject.Inject

class GetDefaultSearchFilterParametersUseCase @Inject constructor() {
    operator fun invoke(): FilterParameters = FilterParameters(
        countryId = 54,
        genreId = 25,
        country = Arguments.ALL_COUNTRY,
        genre = Arguments.ALL_GENRE,
        sorting = Arguments.SORT_BY_RATING_FILTER,
        type = Arguments.ARG_ALL_TYPE,
        ratingFrom = "6",
        ratingTo = "10",
        yearFrom = "1999",
        yearTo = "2023",
        hideWatchedMovies = true
    )
}