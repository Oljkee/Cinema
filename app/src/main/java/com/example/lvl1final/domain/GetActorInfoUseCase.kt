package com.example.lvl1final.domain

import com.example.lvl1final.data.api.ActorInfoDto
import com.example.lvl1final.data.repositories.KinopoiskNetworkRepository
import javax.inject.Inject

class GetActorInfoUseCase @Inject constructor(
    private val kinopoiskNetworkRepository: KinopoiskNetworkRepository
) {
    suspend operator fun invoke(id: Int): ActorInfoDto {
        return kinopoiskNetworkRepository.getActorInfo(id)
    }
}