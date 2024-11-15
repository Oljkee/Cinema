package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.movieimpl.KinopoiskSelectionMoviesImpl
import com.example.lvl1final.domain.repository.MovieRepository
import javax.inject.Inject

class GetSelectionMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        countries: Int,
        genres: Int,
        page: Int
    ): KinopoiskSelectionMoviesImpl {
        return movieRepository.getSelectionMovies(
            countries = countries,
            genres = genres,
            page = page
        )
    }
}