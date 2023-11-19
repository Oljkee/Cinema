package com.example.lvl1final.data.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


data class CollectionWithMovies(
    @Embedded
    val collection: MoviesCollection,
    @Relation(
        parentColumn = "id",
        entityColumn = "kinopoisk_id",
        associateBy = Junction(
            CollectionMovie::class,
            parentColumn = "collection_id",
            entityColumn = "movie_id"
        )
    )
    val movies: List<KinopoiskMovie>
)
