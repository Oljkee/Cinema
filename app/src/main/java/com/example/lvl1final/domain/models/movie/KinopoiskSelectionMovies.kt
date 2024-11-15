package com.example.lvl1final.domain.models.movie

interface KinopoiskSelectionMovies {
    val items: List<Movie>
    val total: Int
    val totalPages: Int
}