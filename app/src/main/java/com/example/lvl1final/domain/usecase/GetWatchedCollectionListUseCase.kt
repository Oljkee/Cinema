package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.collection.WatchedMovieWithKinopoiskMovie
import com.example.lvl1final.domain.repository.WatchedMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWatchedCollectionListUseCase @Inject constructor(
    private val watchedMoviesRepository: WatchedMoviesRepository
) {
    fun getWatchedMovieList(): Flow<List<WatchedMovieWithKinopoiskMovie?>> =
        watchedMoviesRepository.getWatchedMovieList()
}