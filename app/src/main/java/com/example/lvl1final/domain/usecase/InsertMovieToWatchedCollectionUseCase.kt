package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.collection.KinopoiskMovie
import com.example.lvl1final.domain.models.collection.WatchedMovie
import com.example.lvl1final.domain.repository.WatchedMoviesRepository
import javax.inject.Inject

class InsertMovieToWatchedCollectionUseCase @Inject constructor(
    private val watchedMoviesRepository: WatchedMoviesRepository
) {
    suspend fun insertMovieToWatchedCollection(kinopoiskMovie: KinopoiskMovie, watchedMovie: WatchedMovie) {
        watchedMoviesRepository.insertMovieToWatchedCollection(kinopoiskMovie, watchedMovie)
    }

}