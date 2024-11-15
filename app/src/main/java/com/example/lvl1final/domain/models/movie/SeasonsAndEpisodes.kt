package com.example.lvl1final.domain.models.movie

interface SeasonsAndEpisodes{
    val items: List<Season>
    val total: Int
}

interface Season{
    val episodes: List<Episode>
    val number: Int
}

interface Episode{
    val episodeNumber: Int
    val nameEn: String?
    val nameRu: String?
    val releaseDate: String?
    val seasonNumber: Int
    val synopsis: String?
}