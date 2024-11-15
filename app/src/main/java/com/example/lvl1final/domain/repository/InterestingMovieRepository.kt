package com.example.lvl1final.domain.repository

import com.example.lvl1final.domain.models.collection.InterestingMovie
import com.example.lvl1final.domain.models.collection.InterestingMovieWithKinopoiskMovie
import com.example.lvl1final.domain.models.collection.KinopoiskMovie
import kotlinx.coroutines.flow.Flow

interface InterestingMovieRepository {

    fun getInterestingMovieList(): Flow<List<InterestingMovieWithKinopoiskMovie?>>
    suspend fun insertMovieToInterestingCollection(
        kinopoiskMovie: KinopoiskMovie,
        interestingMovie: InterestingMovie
    )
    suspend fun deleteMoviesFromDeletingCollection(collectionId: Int)
}