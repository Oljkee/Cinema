package com.example.lvl1final.domain

import com.example.lvl1final.data.api.MovieStaffDto
import com.example.lvl1final.data.repositories.KinopoiskNetworkRepository
import javax.inject.Inject

class GetMovieStaffInfoUseCase @Inject constructor(
    private val kinopoiskNetworkRepository: KinopoiskNetworkRepository
) {
    suspend operator fun invoke(filmId: Int): List<MovieStaffDto> {
        return kinopoiskNetworkRepository.getMovieStaffInfo(filmId)
    }
}