package com.example.lvl1final.domain.models.movieimpl

import com.example.lvl1final.domain.models.movie.Episode
import com.example.lvl1final.domain.models.movie.Season
import com.example.lvl1final.domain.models.movie.SeasonsAndEpisodes

data class SeasonsAndEpisodesImpl(
    override val items: List<SeasonImpl>,
    override val total: Int
) : SeasonsAndEpisodes

data class SeasonImpl(
    override val episodes: List<EpisodeImpl>,
    override val number: Int
) : Season

data class EpisodeImpl(
    override val episodeNumber: Int,
    override val nameEn: String?,
    override val nameRu: String?,
    override val releaseDate: String?,
    override val seasonNumber: Int,
    override val synopsis: String?
) : Episode