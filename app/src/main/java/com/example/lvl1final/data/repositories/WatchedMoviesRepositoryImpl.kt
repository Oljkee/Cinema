package com.example.lvl1final.data.repositories

import com.example.lvl1final.data.mapper.storage.toDomain
import com.example.lvl1final.data.mapper.storage.toDomainList
import com.example.lvl1final.data.mapper.storage.toEntity
import com.example.lvl1final.data.storage.KinopoiskDao
import com.example.lvl1final.domain.models.collection.KinopoiskMovie
import com.example.lvl1final.domain.models.collection.WatchedMovie
import com.example.lvl1final.domain.models.collection.WatchedMovieWithKinopoiskMovie
import com.example.lvl1final.domain.repository.WatchedMoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WatchedMoviesRepositoryImpl @Inject constructor(private val dao: KinopoiskDao) :
    WatchedMoviesRepository {

    override suspend fun isMovieInWatchedCollection(kinopoiskId: Int): WatchedMovie? {
        return dao.getIdInWatchedCollection(kinopoiskId)?.toDomain()
    }

    override fun getWatchedMovieList(): Flow<List<WatchedMovieWithKinopoiskMovie?>> =
        dao.getWatchedMoviesWithKinopoiskMovies().map { list ->
            list.toDomainList()
        }

    override suspend fun insertMovieToWatchedCollection(
        kinopoiskMovie: KinopoiskMovie,
        watchedMovie: WatchedMovie
    ) {
        val kinopoiskMovieEntity = kinopoiskMovie.toEntity()
        val watchedMovieEntity = watchedMovie.toEntity()
        dao.insertMovie(kinopoiskMovieEntity)
        dao.insertMovieToWatchedCollection(watchedMovieEntity)
    }

    override suspend fun deleteMovieFromWatchedCollection(kinopoiskId: Int) {
        dao.deleteMovieFromWatchedCollection(kinopoiskId)
    }

}