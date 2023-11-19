package com.example.lvl1final.data.entity

data class Collection(
    var id: Int,
    var collectionName: String,
    var numberOfMovies: Int,
    var isMovieInCollection: Boolean = false
)
