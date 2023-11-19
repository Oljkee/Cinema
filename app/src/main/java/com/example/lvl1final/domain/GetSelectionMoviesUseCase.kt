package com.example.lvl1final.domain

import com.example.lvl1final.data.api.KinopoiskSelectionMoviesDto
import com.example.lvl1final.data.repositories.KinopoiskNetworkRepository
import javax.inject.Inject

class GetSelectionMoviesUseCase @Inject constructor(
    private val kinopoiskNetworkRepository: KinopoiskNetworkRepository
) {
    suspend operator fun invoke(countries: Int, genres: Int, page: Int): KinopoiskSelectionMoviesDto {
        return kinopoiskNetworkRepository.getSelectionMovies(countries = countries, genres = genres, page = page)
    }
}