package com.example.lvl1final.domain.repository

import com.example.lvl1final.domain.models.collection.KinopoiskMovie
import com.example.lvl1final.domain.models.collection.WatchedMovie
import com.example.lvl1final.domain.models.collection.WatchedMovieWithKinopoiskMovie
import kotlinx.coroutines.flow.Flow

interface WatchedMoviesRepository {
    suspend fun isMovieInWatchedCollection(kinopoiskId: Int): WatchedMovie?
    fun getWatchedMovieList(): Flow<List<WatchedMovieWithKinopoiskMovie?>>
    suspend fun insertMovieToWatchedCollection(
        kinopoiskMovieEntity: KinopoiskMovie,
        watchedMovieEntity: WatchedMovie
    )
    suspend fun deleteMovieFromWatchedCollection(kinopoiskId: Int)
}