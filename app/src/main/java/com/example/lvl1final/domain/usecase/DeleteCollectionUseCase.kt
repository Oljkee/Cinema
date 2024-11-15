package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.collection.MoviesCollection
import com.example.lvl1final.domain.repository.InterestingMovieRepository
import com.example.lvl1final.domain.repository.MovieCollectionRepository
import javax.inject.Inject

class DeleteCollectionUseCase @Inject constructor(
    private val movieCollectionRepository: MovieCollectionRepository,
    private val interestingMovieRepository: InterestingMovieRepository

) {
    suspend fun deleteCollection(moviesCollection: MoviesCollection) {
        interestingMovieRepository.deleteMoviesFromDeletingCollection(moviesCollection.id!!)
        movieCollectionRepository.deleteCollection(moviesCollection)
    }
}