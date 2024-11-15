package com.example.lvl1final.data.mapper.storage

import com.example.lvl1final.data.storage.entity.KinopoiskMovieEntity
import com.example.lvl1final.domain.models.collection.KinopoiskMovie

fun KinopoiskMovieEntity.toDomain() : KinopoiskMovie {
    return KinopoiskMovie(
        kinopoiskId = this@toDomain.kinopoiskId,
        posterUrl = this@toDomain.posterUrl,
        movieName = this@toDomain.movieName,
        movieGenre = this@toDomain.movieGenre,
        year = this@toDomain.year,
        ratingKinopoisk = this@toDomain.ratingKinopoisk
    )
}

fun KinopoiskMovie.toEntity(): KinopoiskMovieEntity {
    return KinopoiskMovieEntity(
        kinopoiskId = this.kinopoiskId,
        posterUrl = this.posterUrl,
        movieName = this.movieName,
        movieGenre = this.movieGenre,
        year = this.year,
        ratingKinopoisk = this.ratingKinopoisk
    )
}