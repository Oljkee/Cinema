package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.collection.CollectionWithMovies
import com.example.lvl1final.domain.repository.MovieCollectionRepository
import javax.inject.Inject

class GetCollectionWithMoviesUseCase @Inject constructor(
    private val movieCollectionRepository: MovieCollectionRepository
) {
    suspend operator fun invoke(collectionId: Int): CollectionWithMovies? =
        movieCollectionRepository.getCollectionWithMovies(collectionId = collectionId)
}