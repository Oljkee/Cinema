package com.example.lvl1final.domain

import com.example.lvl1final.data.api.KinopoiskMovieInfoDto
import com.example.lvl1final.data.repositories.KinopoiskNetworkRepository
import javax.inject.Inject

class GetMovieInfoUseCase @Inject constructor(
    private val kinopoiskNetworkRepository: KinopoiskNetworkRepository
) {
    suspend operator fun invoke(kinopoiskId: Int) : KinopoiskMovieInfoDto {
        return kinopoiskNetworkRepository.getMovieInfo(kinopoiskId)
    }
}