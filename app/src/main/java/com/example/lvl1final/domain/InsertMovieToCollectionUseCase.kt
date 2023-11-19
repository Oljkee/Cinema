package com.example.lvl1final.domain

import com.example.lvl1final.data.entity.CollectionMovie
import com.example.lvl1final.data.entity.InterestingMovie
import com.example.lvl1final.data.entity.KinopoiskMovie
import com.example.lvl1final.data.entity.WatchedMovie
import com.example.lvl1final.data.repositories.KinopoiskStorageRepository
import javax.inject.Inject

class InsertMovieToCollectionUseCase @Inject constructor(
    private val kinopoiskStorageRepository: KinopoiskStorageRepository
) {
    suspend fun insertMovieToCollection(kinopoiskMovie: KinopoiskMovie, collectionMovie: CollectionMovie){
        kinopoiskStorageRepository.insertMovieToCollection(kinopoiskMovie, collectionMovie)
    }

    suspend fun insertMovieToWatchedCollection(kinopoiskMovie: KinopoiskMovie, watchedMovie: WatchedMovie) {
        kinopoiskStorageRepository.insertMovieToWatchedCollection(kinopoiskMovie, watchedMovie)
    }

    suspend fun insertMovieToInterestingCollection(kinopoiskMovie: KinopoiskMovie, interestingMovie: InterestingMovie) {
        kinopoiskStorageRepository.insertMovieToInterestingCollection(kinopoiskMovie, interestingMovie)
    }
}