package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class UpdateOnBoardingCompleteStateUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke(isCompleted: Boolean){
        return repository.updateOnBoardingCompleted(isCompleted = isCompleted)
    }
}