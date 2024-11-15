package com.example.lvl1final.data.mapper.storage

import com.example.lvl1final.data.storage.entity.WatchedMovieEntity
import com.example.lvl1final.domain.models.collection.WatchedMovie


fun WatchedMovieEntity.toDomain() : WatchedMovie {
    return WatchedMovie(
        id = this@toDomain.id,
        kinopoiskId = this@toDomain.kinopoiskId
    )
}

fun WatchedMovie.toEntity(): WatchedMovieEntity {
    return WatchedMovieEntity(
        id = this.id,
        kinopoiskId = this.kinopoiskId
    )
}