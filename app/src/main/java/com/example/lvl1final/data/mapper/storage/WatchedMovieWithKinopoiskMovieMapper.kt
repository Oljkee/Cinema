package com.example.lvl1final.data.mapper.storage

import com.example.lvl1final.data.storage.entity.WatchedMovieWithKinopoiskMovieEntity
import com.example.lvl1final.domain.models.collection.WatchedMovieWithKinopoiskMovie

fun WatchedMovieWithKinopoiskMovieEntity.toDomain() : WatchedMovieWithKinopoiskMovie {
    return WatchedMovieWithKinopoiskMovie(
        watchedMovie  = this@toDomain.watchedMovieEntity.toDomain(),
        kinopoiskMovie  = this@toDomain.kinopoiskMovieEntity.toDomain()
    )
}

fun List<WatchedMovieWithKinopoiskMovieEntity?>.toDomainList(): List<WatchedMovieWithKinopoiskMovie> {
    return this.filterNotNull().map { it.toDomain() }
}