package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.repository.MovieCollectionRepository
import javax.inject.Inject

class GetCollectionListWithThisMovieUseCase @Inject constructor(
    private val movieCollectionRepository: MovieCollectionRepository
) {
    suspend fun getCollectionIdListWithThisMovie(kinopoiskId: Int) : List<Int>? {
        return movieCollectionRepository.getCollectionIdListWithThisMovie(kinopoiskId)
    }
}