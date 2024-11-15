package com.example.lvl1final.data.storage.entity

data class CollectionEntity(
    var id: Int,
    var collectionName: String,
    var numberOfMovies: Int,
    var isMovieInCollection: Boolean = false
)
