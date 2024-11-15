package com.example.lvl1final.data.mapper.network

import com.example.lvl1final.data.network.api.KinopoiskMovieInfoDto
import com.example.lvl1final.domain.models.movieimpl.KinopoiskMovieInfoImpl

fun KinopoiskMovieInfoDto.toDomain(): KinopoiskMovieInfoImpl {
    return KinopoiskMovieInfoImpl(
        completed = this@toDomain.completed,
        countries = this@toDomain.countries.map { it.toDomain() },
        coverUrl = this@toDomain.coverUrl,
        description = this@toDomain.description,
        editorAnnotation = this@toDomain.editorAnnotation,
        endYear = this@toDomain.endYear,
        filmLength = this@toDomain.filmLength,
        genres = this@toDomain.genres.map { it.toDomain() },
        has3D = this@toDomain.has3D,
        hasImax = this@toDomain.hasImax,
        imdbId = this@toDomain.imdbId,
        isTicketsAvailable = this@toDomain.isTicketsAvailable,
        kinopoiskId = this@toDomain.kinopoiskId,
        lastSync = this@toDomain.lastSync,
        logoUrl = this@toDomain.logoUrl,
        nameEn = this@toDomain.nameEn,
        nameOriginal = this@toDomain.nameOriginal,
        nameRu = this@toDomain.nameRu,
        posterUrl = this@toDomain.posterUrl,
        posterUrlPreview = this@toDomain.posterUrlPreview,
        productionStatus = this@toDomain.productionStatus,
        ratingAgeLimits = this@toDomain.ratingAgeLimits,
        ratingAwait = this@toDomain.ratingAwait,
        ratingAwaitCount = this@toDomain.ratingAwaitCount,
        ratingFilmCritics = this@toDomain.ratingFilmCritics,
        ratingFilmCriticsVoteCount = this@toDomain.ratingFilmCriticsVoteCount,
        ratingGoodReview = this@toDomain.ratingGoodReview,
        ratingGoodReviewVoteCount = this@toDomain.ratingGoodReviewVoteCount,
        ratingImdb = this@toDomain.ratingImdb,
        ratingImdbVoteCount = this@toDomain.ratingImdbVoteCount,
        ratingKinopoisk = this@toDomain.ratingKinopoisk,
        ratingKinopoiskVoteCount = this@toDomain.ratingKinopoiskVoteCount,
        ratingMpaa = this@toDomain.ratingMpaa,
        ratingRfCritics = this@toDomain.ratingRfCritics,
        ratingRfCriticsVoteCount = this@toDomain.ratingRfCriticsVoteCount,
        reviewsCount = this@toDomain.reviewsCount,
        serial = this@toDomain.serial,
        shortDescription = this@toDomain.shortDescription,
        shortFilm = this@toDomain.shortFilm,
        slogan = this@toDomain.slogan,
        startYear = this@toDomain.startYear,
        type = this@toDomain.type,
        webUrl = this@toDomain.webUrl,
        year = this@toDomain.year
    )
}