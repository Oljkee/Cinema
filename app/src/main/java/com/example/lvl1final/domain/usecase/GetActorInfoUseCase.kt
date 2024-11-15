package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.movieimpl.ActorImpl
import com.example.lvl1final.domain.repository.MovieRepository
import javax.inject.Inject

class GetActorInfoUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(id: Int): ActorImpl {
        return movieRepository.getActorInfo(id)
    }
}