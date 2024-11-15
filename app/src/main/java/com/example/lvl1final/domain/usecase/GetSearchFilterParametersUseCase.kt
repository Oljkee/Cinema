package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.movie.FilterParameters
import com.example.lvl1final.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchFilterParametersUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {
    operator fun invoke(): Flow<FilterParameters>{
        return repository.searchFilterParameters
    }
}