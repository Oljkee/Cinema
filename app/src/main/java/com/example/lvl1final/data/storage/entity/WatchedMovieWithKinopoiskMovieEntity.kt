package com.example.lvl1final.data.storage.entity

import androidx.room.Embedded
import androidx.room.Relation

data class WatchedMovieWithKinopoiskMovieEntity(
    @Embedded
    val watchedMovieEntity: WatchedMovieEntity,
    @Relation(
        parentColumn = "kinopoisk_id",
        entityColumn = "kinopoisk_id"
    )
    val kinopoiskMovieEntity: KinopoiskMovieEntity
)