package com.example.lvl1final.data.repositories

import com.example.lvl1final.data.db.KinopoiskDao
import com.example.lvl1final.data.entity.CollectionMovie
import com.example.lvl1final.data.entity.CollectionWithMovies
import com.example.lvl1final.data.entity.InterestingMovie
import com.example.lvl1final.data.entity.InterestingMovieWithKinopoiskMovie
import com.example.lvl1final.data.entity.KinopoiskMovie
import com.example.lvl1final.data.entity.MoviesCollection
import com.example.lvl1final.data.entity.WatchedMovie
import com.example.lvl1final.data.entity.WatchedMovieWithKinopoiskMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KinopoiskStorageRepositoryImpl @Inject constructor(private val dao: KinopoiskDao) : KinopoiskStorageRepository {
    override suspend fun createNewCollection(moviesCollection: MoviesCollection) {
        dao.insertCollection(moviesCollection)
    }

    override suspend fun deleteCollection(moviesCollection: MoviesCollection) {
        dao.deleteCollection(moviesCollection)
    }

    override fun getCollectionList(): Flow<List<MoviesCollection>> = dao.getCollectionList()

    override suspend fun getCollectionWithMovies(collectionId: Int): CollectionWithMovies? =
        dao.getCollectionWithMovies(collectionId = collectionId)

    override suspend fun getCollectionId(collectionName: String): Int =
        dao.getCollectionId(collectionName)

    override suspend fun insertMovieToCollection(
        kinopoiskMovie: KinopoiskMovie,
        collectionMovie: CollectionMovie
    ) {
        dao.insertMovie(kinopoiskMovie)
        dao.insertMovieToCollection(collectionMovie)
        val numberOfMovies = dao.getCollectionWithMovies(collectionMovie.collectionId)!!.movies.size
        dao.setNumberOfMoviesIntoMoviesCollection(collectionMovie.collectionId, numberOfMovies)
    }

    override suspend fun deleteMovieFromCollection(collectionMovie: CollectionMovie) {
        dao.deleteMovieFromCollection(collectionMovie)
        val numberOfMovies = dao.getCollectionWithMovies(collectionMovie.collectionId)!!.movies.size
        dao.setNumberOfMoviesIntoMoviesCollection(collectionMovie.collectionId, numberOfMovies)
    }

    override suspend fun getCollectionIdListWithThisMovie(kinopoiskId: Int): List<Int>? {
        return dao.getCollectionIdListWithThisMovie(kinopoiskId)
    }

    override suspend fun isMovieInWatchedCollection(kinopoiskId: Int): WatchedMovie? {
        return dao.getIdInWatchedCollection(kinopoiskId)
    }

    override fun getWatchedMovieList(): Flow<List<WatchedMovieWithKinopoiskMovie?>> =
        dao.getWatchedMoviesWithKinopoiskMovies()

    override suspend fun insertMovieToWatchedCollection(
        kinopoiskMovie: KinopoiskMovie,
        watchedMovie: WatchedMovie
    ) {
        dao.insertMovie(kinopoiskMovie)
        dao.insertMovieToWatchedCollection(watchedMovie)
    }

    override suspend fun deleteMovieFromWatchedCollection(watchedMovie: WatchedMovie) {
        dao.deleteMovieFromWatchedCollection(watchedMovie)
    }

    override fun getInterestingMovieList(): Flow<List<InterestingMovieWithKinopoiskMovie?>> =
        dao.getInterestingMoviesWithKinopoiskMovies()

    override suspend fun insertMovieToInterestingCollection(
        kinopoiskMovie: KinopoiskMovie,
        interestingMovie: InterestingMovie
    ) {
        dao.insertMovie(kinopoiskMovie)

        val existingMovieId = dao.getInterestingMovieId(interestingMovie.kinopoiskId)

        if (existingMovieId != null) {
            dao.deleteInterestingMovieById(existingMovieId)
        } else {
            val currentSize = dao.getInterestingCollectionSize()
            if (currentSize >= 20) {
                val oldestMovieId = dao.getOldestInterestingMovieId()
                oldestMovieId?.let {
                    dao.deleteInterestingMovieById(it)
                }
            }
        }
        dao.insertOrUpdateInterestingMovie(interestingMovie)
    }
}