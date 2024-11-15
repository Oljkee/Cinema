package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.collection.InterestingMovieWithKinopoiskMovie
import com.example.lvl1final.domain.repository.InterestingMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInterestingCollectionListUseCase @Inject constructor(
    private val interestingMovieRepository: InterestingMovieRepository
) {
    fun getInterestingMovieList(): Flow<List<InterestingMovieWithKinopoiskMovie?>> =
        interestingMovieRepository.getInterestingMovieList()
}