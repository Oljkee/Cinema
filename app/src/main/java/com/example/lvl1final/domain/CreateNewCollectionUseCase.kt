package com.example.lvl1final.domain

import com.example.lvl1final.data.entity.MoviesCollection
import com.example.lvl1final.data.repositories.KinopoiskStorageRepository
import javax.inject.Inject

class CreateNewCollectionUseCase @Inject constructor(
    private val kinopoiskStorageRepository: KinopoiskStorageRepository
) {
    suspend fun createNewCollection(moviesCollection: MoviesCollection) {
        kinopoiskStorageRepository.createNewCollection(moviesCollection)
    }
}