package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.movieimpl.MovieStaffImpl
import com.example.lvl1final.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieStaffInfoUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(filmId: Int): List<MovieStaffImpl> {
        return movieRepository.getMovieStaffInfo(filmId)
    }
}