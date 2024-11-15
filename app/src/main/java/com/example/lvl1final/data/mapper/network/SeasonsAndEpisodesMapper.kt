package com.example.lvl1final.data.mapper.network

import com.example.lvl1final.data.network.api.EpisodeDto
import com.example.lvl1final.data.network.api.SeasonDto
import com.example.lvl1final.data.network.api.SeasonsAndEpisodesDto
import com.example.lvl1final.domain.models.movieimpl.EpisodeImpl
import com.example.lvl1final.domain.models.movieimpl.SeasonImpl
import com.example.lvl1final.domain.models.movieimpl.SeasonsAndEpisodesImpl

fun SeasonsAndEpisodesDto.toDomain() : SeasonsAndEpisodesImpl {
    return SeasonsAndEpisodesImpl(
        items = this@toDomain.items.map { it.toDomain() },
        total = this@toDomain.total
    )
}

fun SeasonDto.toDomain() : SeasonImpl {
    return SeasonImpl(
        episodes = this@toDomain.episodes.map { it.toDomain() },
        number = this@toDomain.number
    )
}

fun EpisodeDto.toDomain() : EpisodeImpl {
    return EpisodeImpl(
        episodeNumber = this@toDomain.episodeNumber,
        nameEn = this@toDomain.nameEn,
        nameRu = this@toDomain.nameRu,
        releaseDate = this@toDomain.releaseDate,
        seasonNumber = this@toDomain.seasonNumber,
        synopsis = this@toDomain.synopsis
    )
}
