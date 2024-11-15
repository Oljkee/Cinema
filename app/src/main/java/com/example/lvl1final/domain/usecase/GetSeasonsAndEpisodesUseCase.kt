package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.movieimpl.SeasonsAndEpisodesImpl
import com.example.lvl1final.domain.repository.MovieRepository
import javax.inject.Inject

class GetSeasonsAndEpisodesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(id: Int): SeasonsAndEpisodesImpl {
        return movieRepository.getSeasonsAndEpisodes(id)
    }
}