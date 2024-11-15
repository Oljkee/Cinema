package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.movieimpl.SimilarMoviesItemImpl
import com.example.lvl1final.domain.repository.MovieRepository
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(id: Int): List<SimilarMoviesItemImpl> {
        return movieRepository.getSimilarMovies(id = id)
    }
}