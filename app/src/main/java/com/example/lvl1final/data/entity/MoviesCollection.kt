package com.example.lvl1final.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "movies_collection",
    indices = [Index(value = ["collection_name"], unique = true)]
)
data class MoviesCollection(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "collection_name")
    val collectionName: String,
    @ColumnInfo(name = "collection_icon")
    val collectionIcon: Int? = null,
    @ColumnInfo(name = "number_of_movies")
    val numberOfMovies: Int = 0,
    @ColumnInfo(name = "deletable")
    val deletable: Boolean = true
)

