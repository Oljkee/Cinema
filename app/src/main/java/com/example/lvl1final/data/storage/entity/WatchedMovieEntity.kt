package com.example.lvl1final.data.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "watched_movie",
    indices = [Index(value = ["kinopoisk_id"], unique = true)]
)
data class WatchedMovieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "kinopoisk_id")
    val kinopoiskId: Int
)