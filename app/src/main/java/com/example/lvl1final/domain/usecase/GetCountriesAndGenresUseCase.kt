package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.movieimpl.KinopoiskMovieSelectionFiltersImpl
import com.example.lvl1final.domain.repository.MovieRepository
import javax.inject.Inject

class GetCountriesAndGenresUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): KinopoiskMovieSelectionFiltersImpl {
        return movieRepository.getMovieSelectionFilters()
    }
}