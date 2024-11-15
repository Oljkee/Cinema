package com.example.lvl1final.data.repositories

import com.example.lvl1final.data.mapper.storage.toDomainList
import com.example.lvl1final.data.mapper.storage.toEntity
import com.example.lvl1final.data.storage.KinopoiskDao
import com.example.lvl1final.domain.models.collection.InterestingMovie
import com.example.lvl1final.domain.models.collection.InterestingMovieWithKinopoiskMovie
import com.example.lvl1final.domain.models.collection.KinopoiskMovie
import com.example.lvl1final.domain.repository.InterestingMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class InterestingMovieRepositoryImpl @Inject constructor(private val dao: KinopoiskDao) :
    InterestingMovieRepository {

    override fun getInterestingMovieList(): Flow<List<InterestingMovieWithKinopoiskMovie?>> =
        dao.getInterestingMoviesWithKinopoiskMovies().map { list ->
            list.toDomainList()
        }

    override suspend fun insertMovieToInterestingCollection(
        kinopoiskMovie: KinopoiskMovie,
        interestingMovie: InterestingMovie
    ) {
        val kinopoiskMovieEntity = kinopoiskMovie.toEntity()
        val interestingMovieEntity = interestingMovie.toEntity()
        dao.insertMovie(kinopoiskMovieEntity)

        val existingMovieId = dao.getInterestingMovieId(interestingMovieEntity.kinopoiskId)

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
        dao.insertOrUpdateInterestingMovie(interestingMovieEntity)
    }

    override suspend fun deleteMoviesFromDeletingCollection(collectionId: Int) {
        dao.deleteMoviesFromDeletingCollection(collectionId)
    }
}