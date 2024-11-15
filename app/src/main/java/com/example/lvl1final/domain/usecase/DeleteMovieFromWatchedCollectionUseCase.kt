package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.repository.WatchedMoviesRepository
import javax.inject.Inject

class DeleteMovieFromWatchedCollectionUseCase @Inject constructor(
    private val watchedMoviesRepository: WatchedMoviesRepository
) {
    suspend operator fun invoke(kinopoiskId: Int) {
        watchedMoviesRepository.deleteMovieFromWatchedCollection(kinopoiskId)
    }
}