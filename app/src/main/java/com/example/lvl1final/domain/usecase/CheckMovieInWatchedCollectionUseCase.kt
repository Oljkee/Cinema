package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.collection.WatchedMovie
import com.example.lvl1final.domain.repository.WatchedMoviesRepository
import javax.inject.Inject

class CheckMovieInWatchedCollectionUseCase @Inject constructor(
    private val watchedMoviesRepository: WatchedMoviesRepository
) {
    suspend fun isMovieInWatchedCollection(kinopoiskId: Int): WatchedMovie? {
        return watchedMoviesRepository.isMovieInWatchedCollection(kinopoiskId)
    }
}