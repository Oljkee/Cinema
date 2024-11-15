package com.example.lvl1final.domain.models.movieimpl

import com.example.lvl1final.domain.models.movie.Movie

data class MovieImpl (
    override val duration: Int?,
    override val premiereRu: String?,

    override val countries: List<CountryImpl>,
    override val filmId: Int?,
    override val filmLength: String?,
    override val genres: List<GenreImpl>,
    override val nameEn: String?,
    override val nameRu: String?,
    override val posterUrl: String,
    override val posterUrlPreview: String,
    override val rating: String?,
    override val ratingChange: Any?,
    override val ratingVoteCount: Int?,
    override val year: Int?,

    override val imdbId: String?,
    override val kinopoiskId: Int?,
    override val nameOriginal: String?,
    override val ratingImdb: Double?,
    override val ratingKinopoisk: Double?,
    override val type: String?
) : Movie