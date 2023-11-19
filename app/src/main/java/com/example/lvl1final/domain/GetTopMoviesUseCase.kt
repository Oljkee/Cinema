package com.example.lvl1final.domain

import com.example.lvl1final.data.api.MovieDto
import com.example.lvl1final.data.repositories.KinopoiskNetworkRepository
import javax.inject.Inject

class GetTopMoviesUseCase @Inject constructor(
    private val kinopoiskNetworkRepository: KinopoiskNetworkRepository
) {
    suspend operator fun invoke(page: Int, type: String): List<MovieDto> {
        return kinopoiskNetworkRepository.getTopMovies(page = page, type = type)
    }
}