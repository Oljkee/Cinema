package com.example.lvl1final.domain

import com.example.lvl1final.data.repositories.KinopoiskStorageRepository
import javax.inject.Inject

class GetCollectionListWithThisMovieUseCase @Inject constructor(
    private val kinopoiskStorageRepository: KinopoiskStorageRepository
) {
    suspend fun getCollectionIdListWithThisMovie(kinopoiskId: Int) : List<Int>? {
        return kinopoiskStorageRepository.getCollectionIdListWithThisMovie(kinopoiskId)
    }
}