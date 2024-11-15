package com.example.lvl1final.domain.repository

import com.example.lvl1final.domain.models.movie.FilterParameters
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    suspend fun updateOnBoardingCompleted(isCompleted: Boolean) {}
    val isOnBoardingCompletedFlow: Flow<Boolean>
    suspend fun updateSearchFilterParameters(filterParameters: FilterParameters) {}
    val searchFilterParameters: Flow<FilterParameters>

}