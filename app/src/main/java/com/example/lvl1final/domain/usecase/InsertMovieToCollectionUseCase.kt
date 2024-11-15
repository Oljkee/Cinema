package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.collection.CollectionMovie
import com.example.lvl1final.domain.models.collection.KinopoiskMovie
import com.example.lvl1final.domain.repository.MovieCollectionRepository
import javax.inject.Inject

class InsertMovieToCollectionUseCase @Inject constructor(
    private val movieCollectionRepository: MovieCollectionRepository
) {
    suspend fun insertMovieToCollection(kinopoiskMovie: KinopoiskMovie, collectionMovie: CollectionMovie){
        movieCollectionRepository.insertMovieToCollection(kinopoiskMovie, collectionMovie)
    }
}