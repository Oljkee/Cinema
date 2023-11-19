package com.example.lvl1final.domain

import com.example.lvl1final.data.repositories.KinopoiskStorageRepository
import javax.inject.Inject

class GetCollectionIdUseCase @Inject constructor(
    private val kinopoiskStorageRepository: KinopoiskStorageRepository
) {
    suspend operator fun invoke(collectionName: String): Int =
        kinopoiskStorageRepository.getCollectionId(collectionName)
}