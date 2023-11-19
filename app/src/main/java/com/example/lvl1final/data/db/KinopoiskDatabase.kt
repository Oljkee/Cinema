package com.example.lvl1final.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lvl1final.data.entity.CollectionMovie
import com.example.lvl1final.data.entity.InterestingMovie
import com.example.lvl1final.data.entity.KinopoiskMovie
import com.example.lvl1final.data.entity.MoviesCollection
import com.example.lvl1final.data.entity.WatchedMovie

@Database(
    entities = [
        KinopoiskMovie::class,
        MoviesCollection::class,
        CollectionMovie::class,
        WatchedMovie::class,
        InterestingMovie::class
    ],
    version = 1
)
abstract class KinopoiskDatabase : RoomDatabase() {

    abstract fun kinopoiskDao(): KinopoiskDao
}