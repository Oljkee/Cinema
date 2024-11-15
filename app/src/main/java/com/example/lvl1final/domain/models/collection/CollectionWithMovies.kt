package com.example.lvl1final.domain.models.collection

data class CollectionWithMovies (
    val collection: MoviesCollection,
    val movies: List<KinopoiskMovie>
)