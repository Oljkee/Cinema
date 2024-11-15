package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.movieimpl.MovieImpl
import com.example.lvl1final.domain.repository.MovieRepository
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
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
    ): List<MovieImpl> {
        return movieRepository.searchMovie(
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