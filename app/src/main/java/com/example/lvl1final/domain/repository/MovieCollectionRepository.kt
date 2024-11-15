package com.example.lvl1final.domain.repository

import com.example.lvl1final.domain.models.collection.CollectionMovie
import com.example.lvl1final.domain.models.collection.CollectionWithMovies
import com.example.lvl1final.domain.models.collection.KinopoiskMovie
import com.example.lvl1final.domain.models.collection.MoviesCollection
import kotlinx.coroutines.flow.Flow

interface MovieCollectionRepository {
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
}