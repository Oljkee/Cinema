package com.example.lvl1final.domain

import com.example.lvl1final.data.api.MovieDto
import com.example.lvl1final.data.repositories.KinopoiskNetworkRepository
import javax.inject.Inject

class GetSeriesUseCase @Inject constructor(
    private val kinopoiskNetworkRepository: KinopoiskNetworkRepository
) {
    suspend operator fun invoke(page: Int): List<MovieDto> {
        return kinopoiskNetworkRepository.getSeries(page = page)
    }
}