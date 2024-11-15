package com.example.lvl1final.data.storage.entity

import androidx.room.Embedded
import androidx.room.Relation

data class InterestingMovieWithKinopoiskMovieEntity(
    @Embedded
    val interestingMovieEntity: InterestingMovieEntity,
    @Relation(
        parentColumn = "kinopoisk_id",
        entityColumn = "kinopoisk_id"
    )
    val kinopoiskMovieEntity: KinopoiskMovieEntity
)