package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.movieimpl.ImageImpl
import com.example.lvl1final.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviePageImagesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(id: Int, types: List<String>): List<ImageImpl> {
        var result = listOf<ImageImpl>()
        types.forEach{ type ->
            val images = movieRepository.getMovieImages(id = id, type = type)
            if (images.isNotEmpty()) {
                result = images
                return result
            }
        }
        return result
    }
}