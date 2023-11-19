package com.example.lvl1final.data.repositories

import com.example.lvl1final.data.entity.CollectionMovie
import com.example.lvl1final.data.entity.CollectionWithMovies
import com.example.lvl1final.data.entity.InterestingMovie
import com.example.lvl1final.data.entity.InterestingMovieWithKinopoiskMovie
import com.example.lvl1final.data.entity.KinopoiskMovie
import com.example.lvl1final.data.entity.MoviesCollection
import com.example.lvl1final.data.entity.WatchedMovie
import com.example.lvl1final.data.entity.WatchedMovieWithKinopoiskMovie
import kotlinx.coroutines.flow.Flow

interface KinopoiskStorageRepository {
    suspend fun createNewCollection(moviesCollection: MoviesCollection)
    suspend fun deleteCollection(moviesCollection: MoviesCollection)
    fun getCollectionList(): Flow<List<MoviesCollection>>
    suspend fun getCollectionWithMovies(collectionId: Int): CollectionWithMovies?
    suspend fun getCollectionId(collectionName: String): Int
    suspend fun insertMovieToCollection(
        kinopoiskMovie: KinopoiskMovie,
        collectionMovie: CollectionMovie
    )
    suspend fun deleteMovieFromCollection(collectionMovie: CollectionMovie)
    suspend fun getCollectionIdListWithThisMovie(kinopoiskId: Int): List<Int>?
    suspend fun isMovieInWatchedCollection(kinopoiskId: Int): WatchedMovie?
    fun getWatchedMovieList(): Flow<List<WatchedMovieWithKinopoiskMovie?>>
    suspend fun insertMovieToWatchedCollection(
        kinopoiskMovie: KinopoiskMovie,
        watchedMovie: WatchedMovie
    )
    suspend fun deleteMovieFromWatchedCollection(watchedMovie: WatchedMovie)
    fun getInterestingMovieList(): Flow<List<InterestingMovieWithKinopoiskMovie?>>
    suspend fun insertMovieToInterestingCollection(
        kinopoiskMovie: KinopoiskMovie,
        interestingMovie: InterestingMovie
    )
}