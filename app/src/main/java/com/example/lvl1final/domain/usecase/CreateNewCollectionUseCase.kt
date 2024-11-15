package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.collection.MoviesCollection
import com.example.lvl1final.domain.repository.MovieCollectionRepository
import javax.inject.Inject

class CreateNewCollectionUseCase @Inject constructor(
    private val movieCollectionRepository: MovieCollectionRepository
) {
    suspend fun createNewCollection(newCollectionName: String, collectionIcon: Int) {
        val newCollection = MoviesCollection(
            collectionName = newCollectionName,
            collectionIcon = collectionIcon
        )
        movieCollectionRepository.createNewCollection(newCollection)
    }
}