package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.collection.InterestingMovie
import com.example.lvl1final.domain.models.collection.KinopoiskMovie
import com.example.lvl1final.domain.repository.InterestingMovieRepository
import javax.inject.Inject

class InsertMovieToInterestingCollectionUseCase @Inject constructor(
    private val interestingMovieRepository: InterestingMovieRepository
) {
    suspend fun insertMovieToInterestingCollection(
        kinopoiskMovie: KinopoiskMovie,
        interestingMovie: InterestingMovie
    ) {
        interestingMovieRepository.insertMovieToInterestingCollection(
            kinopoiskMovie,
            interestingMovie
        )
    }
}