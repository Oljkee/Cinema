package com.example.lvl1final.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class WatchedMovieWithKinopoiskMovie(
    @Embedded
    val watchedMovie: WatchedMovie,
    @Relation(
        parentColumn = "kinopoisk_id",
        entityColumn = "kinopoisk_id"
    )
    val kinopoiskMovie: KinopoiskMovie
)