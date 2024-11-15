package com.example.lvl1final.domain.models.movie

interface SimilarMovies {
    val items: List<SimilarMoviesItem>
    val total: Int
}

interface SimilarMoviesItem {
    val filmId: Int
    val nameEn: String?
    val nameOriginal: String?
    val nameRu: String?
    val posterUrl: String
    val posterUrlPreview: String
    val relationType: String
}