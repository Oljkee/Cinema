package com.example.lvl1final.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lvl1final.data.storage.entity.CollectionMovieEntity
import com.example.lvl1final.data.storage.entity.InterestingMovieEntity
import com.example.lvl1final.data.storage.entity.KinopoiskMovieEntity
import com.example.lvl1final.data.storage.entity.MoviesCollectionEntity
import com.example.lvl1final.data.storage.entity.WatchedMovieEntity

@Database(
    entities = [
        KinopoiskMovieEntity::class,
        MoviesCollectionEntity::class,
        CollectionMovieEntity::class,
        WatchedMovieEntity::class,
        InterestingMovieEntity::class
    ],
    version = 1
)
abstract class KinopoiskDatabase : RoomDatabase() {

    abstract fun kinopoiskDao(): KinopoiskDao
}