package com.example.lvl1final.domain

import com.example.lvl1final.data.entity.WatchedMovie
import com.example.lvl1final.data.repositories.KinopoiskStorageRepository
import javax.inject.Inject

class CheckMovieInWatchedCollectionUseCase @Inject constructor(
    private val kinopoiskStorageRepository: KinopoiskStorageRepository
) {
    suspend fun isMovieInWatchedCollection(kinopoiskId: Int): WatchedMovie? {
        return kinopoiskStorageRepository.isMovieInWatchedCollection(kinopoiskId)
    }
}