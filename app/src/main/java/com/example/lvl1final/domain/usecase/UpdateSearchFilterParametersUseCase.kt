package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.movie.FilterParameters
import com.example.lvl1final.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class UpdateSearchFilterParametersUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke(filterParameters: FilterParameters){
        return repository.updateSearchFilterParameters(filterParameters = filterParameters)
    }
}