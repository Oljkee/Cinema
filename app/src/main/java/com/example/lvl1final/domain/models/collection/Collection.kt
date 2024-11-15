package com.example.lvl1final.domain.models.collection

data class Collection (
    var id: Int,
    var collectionName: String,
    var numberOfMovies: Int,
    var isMovieInCollection: Boolean
)