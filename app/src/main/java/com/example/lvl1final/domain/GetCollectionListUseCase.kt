package com.example.lvl1final.domain

import com.example.lvl1final.data.entity.InterestingMovieWithKinopoiskMovie
import com.example.lvl1final.data.entity.MoviesCollection
import com.example.lvl1final.data.entity.WatchedMovieWithKinopoiskMovie
import com.example.lvl1final.data.repositories.KinopoiskStorageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollectionListUseCase @Inject constructor(
    private val kinopoiskStorageRepository: KinopoiskStorageRepository
) {
    fun getCollectionList(): Flow<List<MoviesCollection>> = kinopoiskStorageRepository.getCollectionList()

    fun getWatchedMovieList(): Flow<List<WatchedMovieWithKinopoiskMovie?>> =
        kinopoiskStorageRepository.getWatchedMovieList()

    fun getInterestingMovieList(): Flow<List<InterestingMovieWithKinopoiskMovie?>> =
        kinopoiskStorageRepository.getInterestingMovieList()
}