package com.example.lvl1final.data.repositories

import com.example.lvl1final.data.mapper.storage.toDomain
import com.example.lvl1final.data.mapper.storage.toDomainList
import com.example.lvl1final.data.mapper.storage.toEntity
import com.example.lvl1final.data.storage.KinopoiskDao
import com.example.lvl1final.domain.models.collection.CollectionMovie
import com.example.lvl1final.domain.models.collection.CollectionWithMovies
import com.example.lvl1final.domain.models.collection.KinopoiskMovie
import com.example.lvl1final.domain.models.collection.MoviesCollection
import com.example.lvl1final.domain.repository.MovieCollectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieCollectionRepositoryImpl @Inject constructor(private val dao: KinopoiskDao) :
    MovieCollectionRepository {
    override suspend fun createNewCollection(moviesCollection: MoviesCollection) {
        val moviesCollectionEntity = moviesCollection.toEntity()
        dao.insertCollection(moviesCollectionEntity)
    }

    override suspend fun deleteCollection(moviesCollection: MoviesCollection) {
        val moviesCollectionEntity = moviesCollection.toEntity()
        dao.deleteCollection(moviesCollectionEntity)
    }

    override fun getCollectionList(): Flow<List<MoviesCollection>> =
        dao.getCollectionList().map { list ->
            list.toDomainList()
        }

    override suspend fun getCollectionWithMovies(collectionId: Int): CollectionWithMovies? =
        dao.getCollectionWithMovies(collectionId = collectionId)?.toDomain()

    override suspend fun getCollectionId(collectionName: String): Int =
        dao.getCollectionId(collectionName)

    override suspend fun insertMovieToCollection(
        kinopoiskMovie: KinopoiskMovie,
        collectionMovie: CollectionMovie
    ) {
        val kinopoiskMovieEntity = kinopoiskMovie.toEntity()
        val collectionMovieEntity = collectionMovie.toEntity()
        dao.insertMovie(kinopoiskMovieEntity)
        dao.insertMovieToCollection(collectionMovieEntity)
        val numberOfMovies =
            dao.getCollectionWithMovies(collectionMovieEntity.collectionId)!!.movies.size
        dao.setNumberOfMoviesIntoMoviesCollection(
            collectionMovieEntity.collectionId,
            numberOfMovies
        )
    }

    override suspend fun deleteMovieFromCollection(collectionMovie: CollectionMovie) {
        val collectionMovieEntity = collectionMovie.toEntity()
        dao.deleteMovieFromCollection(collectionMovieEntity)
        val numberOfMovies =
            dao.getCollectionWithMovies(collectionMovieEntity.collectionId)!!.movies.size
        dao.setNumberOfMoviesIntoMoviesCollection(
            collectionMovieEntity.collectionId,
            numberOfMovies
        )
    }

    override suspend fun getCollectionIdListWithThisMovie(kinopoiskId: Int): List<Int>? {
        return dao.getCollectionIdListWithThisMovie(kinopoiskId)
    }
}