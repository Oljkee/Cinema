package com.example.lvl1final.domain.models.movie

interface Movie {
    val duration: Int?
    val premiereRu: String?

    val countries: List<Country>
    val filmId: Int?
    val filmLength: String?
    val genres: List<Genre>
    val nameEn: String?
    val nameRu: String?
    val posterUrl: String
    val posterUrlPreview: String
    val rating: String?
    val ratingChange: Any?
    val ratingVoteCount: Int?
    val year: Int?

    val imdbId: String?
    val kinopoiskId: Int?
    val nameOriginal: String?
    val ratingImdb: Double?
    val ratingKinopoisk: Double?
    val type: String?
}