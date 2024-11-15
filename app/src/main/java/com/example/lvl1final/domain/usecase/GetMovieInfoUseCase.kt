package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.movieimpl.KinopoiskMovieInfoImpl
import com.example.lvl1final.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieInfoUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(kinopoiskId: Int) : KinopoiskMovieInfoImpl {
        return movieRepository.getMovieInfo(kinopoiskId)
    }
}