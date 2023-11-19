package com.example.lvl1final.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "interesting_movie",
    indices = [Index(value = ["kinopoisk_id"], unique = true)]
)
data class InterestingMovie(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null,
    @ColumnInfo(name = "kinopoisk_id")
    val kinopoiskId: Int
)