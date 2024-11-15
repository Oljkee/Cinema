package com.example.lvl1final.data.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kinopoisk_movie")
data class KinopoiskMovieEntity (
    @PrimaryKey
    @ColumnInfo(name = "kinopoisk_id")
    val kinopoiskId: Int,
    @ColumnInfo(name = "poster_url")
    val posterUrl: String,
    @ColumnInfo(name = "movie_name")
    val movieName: String? = null,
    @ColumnInfo(name = "movie_genre")
    val movieGenre: String? = null,
    @ColumnInfo(name = "year")
    val year: Int? = null,
    @ColumnInfo(name = "rating_kinopoisk")
    val ratingKinopoisk : Double? = null
)