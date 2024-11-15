package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.movieimpl.ImageImpl
import com.example.lvl1final.domain.repository.MovieRepository
import javax.inject.Inject

class GetAllMovieImagesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(id: Int, types: List<String>): Map<String, List<ImageImpl>> {
        val resultMap = mutableMapOf<String, List<ImageImpl>>()
        types.forEach { type ->
            val images = movieRepository.getMovieImages(id = id, type = type)
            if (images.isNotEmpty()) resultMap[type] = images
        }
        return resultMap
    }
}