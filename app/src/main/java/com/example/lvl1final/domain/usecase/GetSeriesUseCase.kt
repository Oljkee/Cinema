package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.movieimpl.MovieImpl
import com.example.lvl1final.domain.repository.MovieRepository
import javax.inject.Inject

class GetSeriesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(page: Int): List<MovieImpl> {
        return movieRepository.getSeries(page = page)
    }
}