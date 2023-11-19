package com.example.lvl1final.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "collection_movie",
    primaryKeys = ["collection_id", "movie_id"]
)
data class CollectionMovie(
    @ColumnInfo(name = "collection_id")
    val collectionId: Int,
    @ColumnInfo(name = "movie_id")
    val movieId: Int
)

