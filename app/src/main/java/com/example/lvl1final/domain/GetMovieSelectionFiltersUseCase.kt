package com.example.lvl1final.domain

import com.example.lvl1final.data.api.KinopoiskMovieSelectionFiltersDto
import com.example.lvl1final.data.repositories.KinopoiskNetworkRepository
import javax.inject.Inject

class GetMovieSelectionFiltersUseCase @Inject constructor(
    private val kinopoiskNetworkRepository: KinopoiskNetworkRepository
) {
    suspend operator fun invoke(): KinopoiskMovieSelectionFiltersDto {
        return kinopoiskNetworkRepository.getMovieSelectionFilters()
    }
}