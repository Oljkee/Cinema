package com.example.lvl1final.domain.models.collection

data class KinopoiskMovie (
    val kinopoiskId: Int,
    val posterUrl: String,
    val movieName: String?,
    val movieGenre: String?,
    val year: Int?,
    val ratingKinopoisk : Double? = null
)