package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.collection.CollectionMovie
import com.example.lvl1final.domain.repository.MovieCollectionRepository
import javax.inject.Inject

class DeleteMovieFromCollectionUseCase @Inject constructor(
    private val movieCollectionRepository: MovieCollectionRepository
) {
    suspend fun deleteMovieFromCollection(collectionMovie: CollectionMovie) {
        movieCollectionRepository.deleteMovieFromCollection(collectionMovie)
    }
}