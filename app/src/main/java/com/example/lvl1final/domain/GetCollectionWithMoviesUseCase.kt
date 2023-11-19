package com.example.lvl1final.domain

import com.example.lvl1final.data.entity.CollectionWithMovies
import com.example.lvl1final.data.repositories.KinopoiskStorageRepository
import javax.inject.Inject

class GetCollectionWithMoviesUseCase @Inject constructor(
    private val kinopoiskStorageRepository: KinopoiskStorageRepository
) {
    suspend operator fun invoke(collectionId: Int): CollectionWithMovies? =
        kinopoiskStorageRepository.getCollectionWithMovies(collectionId = collectionId)
}