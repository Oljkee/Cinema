package com.example.lvl1final.domain.models.movie

data class FilterParameters(
    val countryId : Int,
    val genreId: Int,
    val country: String,
    val genre: String,
    val sorting: String,
    val type: String,
    val ratingFrom: String,
    val ratingTo: String,
    val yearFrom: String,
    val yearTo : String,
    val hideWatchedMovies: Boolean
)
