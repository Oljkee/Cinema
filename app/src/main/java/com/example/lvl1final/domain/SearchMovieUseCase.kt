package com.example.lvl1final.domain

import com.example.lvl1final.data.api.MovieDto
import com.example.lvl1final.data.repositories.KinopoiskNetworkRepository
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val kinopoiskNetworkRepository: KinopoiskNetworkRepository
) {
    suspend operator fun invoke(
        type: String,
        countries: Int?,
        genres: Int?,
        yearFrom: Int,
        yearTo: Int,
        ratingFrom: Int,
        ratingTo: Int,
        order: String,
        keyword: String,
        page: Int
    ): List<MovieDto> {
        return kinopoiskNetworkRepository.searchMovie(
            type = type,
            countries = countries,
            genres = genres,
            yearFrom = yearFrom,
            yearTo = yearTo,
            ratingFrom = ratingFrom,
            ratingTo = ratingTo,
            order = order,
            keyword = keyword,
            page = page
        )
    }
}