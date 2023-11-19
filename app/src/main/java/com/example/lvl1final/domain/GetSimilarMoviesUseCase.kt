package com.example.lvl1final.domain

import com.example.lvl1final.data.api.SimilarMoviesItemDto
import com.example.lvl1final.data.repositories.KinopoiskNetworkRepository
import javax.inject.Inject

class GetSimilarMoviesUseCase @Inject constructor(
    private val kinopoiskNetworkRepository: KinopoiskNetworkRepository
) {
    suspend operator fun invoke(id: Int): List<SimilarMoviesItemDto> {
        return kinopoiskNetworkRepository.getSimilarMovies(id = id)
    }
}