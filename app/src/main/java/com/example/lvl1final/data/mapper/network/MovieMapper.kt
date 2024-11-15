package com.example.lvl1final.data.mapper.network

import com.example.lvl1final.data.network.api.MovieDto
import com.example.lvl1final.domain.models.movieimpl.MovieImpl

fun MovieDto.toDomain(): MovieImpl {
    return MovieImpl(
        duration = this@toDomain.duration,
        premiereRu = this@toDomain.premiereRu,
        countries = this@toDomain.countries.map { it.toDomain() },
        filmId = this@toDomain.filmId,
        filmLength = this@toDomain.filmLength,
        genres = this@toDomain.genres.map { it.toDomain() },
        nameEn = this@toDomain.nameEn,
        nameRu = this@toDomain.nameRu,
        posterUrl = this@toDomain.posterUrl,
        posterUrlPreview = this@toDomain.posterUrlPreview,
        rating = this@toDomain.rating,
        ratingChange = this@toDomain.ratingChange,
        ratingVoteCount = this@toDomain.ratingVoteCount,
        year = this@toDomain.year,
        imdbId = this@toDomain.imdbId,
        kinopoiskId = this@toDomain.kinopoiskId,
        nameOriginal = this@toDomain.nameOriginal,
        ratingImdb = this@toDomain.ratingImdb,
        ratingKinopoisk = this@toDomain.ratingKinopoisk,
        type = this@toDomain.type
    )
}