package com.example.lvl1final.domain

import com.example.lvl1final.data.entity.CollectionMovie
import com.example.lvl1final.data.entity.WatchedMovie
import com.example.lvl1final.data.repositories.KinopoiskStorageRepository
import javax.inject.Inject

class DeleteMovieFromCollectionUseCase @Inject constructor(
    private val kinopoiskStorageRepository: KinopoiskStorageRepository
) {
    suspend fun deleteMovieFromCollection(collectionMovie: CollectionMovie) {
        kinopoiskStorageRepository.deleteMovieFromCollection(collectionMovie)
    }

    suspend fun deleteMovieFromWatchedCollection(watchedMovie: WatchedMovie) {
        kinopoiskStorageRepository.deleteMovieFromWatchedCollection(watchedMovie)
    }
}