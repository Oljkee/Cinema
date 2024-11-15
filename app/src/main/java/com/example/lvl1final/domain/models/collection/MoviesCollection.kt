package com.example.lvl1final.domain.models.collection

data class MoviesCollection(
    val id: Int? = null,
    val collectionName: String,
    val collectionIcon: Int? = null,
    val numberOfMovies: Int = 0,
    val deletable: Boolean = true
)

