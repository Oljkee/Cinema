package com.example.lvl1final.domain

import com.example.lvl1final.data.api.SeasonsAndEpisodesDto
import com.example.lvl1final.data.repositories.KinopoiskNetworkRepository
import javax.inject.Inject

class GetSeasonsAndEpisodesUseCase @Inject constructor(
    private val kinopoiskNetworkRepository: KinopoiskNetworkRepository
) {
    suspend operator fun invoke(id: Int): SeasonsAndEpisodesDto {
        return kinopoiskNetworkRepository.getSeasonsAndEpisodes(id)
    }
}