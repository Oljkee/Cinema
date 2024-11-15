package com.example.lvl1final.data.storage.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


data class CollectionWithMoviesEntity(
    @Embedded
    val collection: MoviesCollectionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "kinopoisk_id",
        associateBy = Junction(
            CollectionMovieEntity::class,
            parentColumn = "collection_id",
            entityColumn = "movie_id"
        )
    )
    val movies: List<KinopoiskMovieEntity>
)
