package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOnBoardingCompleteStateUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {
    operator fun invoke(): Flow<Boolean>{
        return repository.isOnBoardingCompletedFlow
    }
}