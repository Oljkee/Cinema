package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.collection.MoviesCollection
import com.example.lvl1final.domain.repository.MovieCollectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollectionListUseCase @Inject constructor(
    private val movieCollectionRepository: MovieCollectionRepository
) {
    fun getOtherCollectionList(): Flow<List<MoviesCollection>> =
        movieCollectionRepository.getCollectionList()
}