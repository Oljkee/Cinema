package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.repository.MovieCollectionRepository
import javax.inject.Inject

class GetCollectionIdUseCase @Inject constructor(
    private val movieCollectionRepository: MovieCollectionRepository
) {
    suspend operator fun invoke(collectionName: String): Int =
        movieCollectionRepository.getCollectionId(collectionName)
}