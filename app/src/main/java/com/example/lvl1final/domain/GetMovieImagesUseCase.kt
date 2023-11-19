package com.example.lvl1final.domain

import com.example.lvl1final.data.api.ImageDto
import com.example.lvl1final.data.repositories.KinopoiskNetworkRepository
import javax.inject.Inject

class GetMovieImagesUseCase @Inject constructor(
    private val kinopoiskNetworkRepository: KinopoiskNetworkRepository
) {
    suspend operator fun invoke(id: Int, type: String): List<ImageDto> {
        return kinopoiskNetworkRepository.getMovieImages(id = id, type = type)
    }
}