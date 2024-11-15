package com.example.lvl1final.data.mapper.network

import com.example.lvl1final.data.network.api.KinopoiskMovieSelectionFiltersDto
import com.example.lvl1final.domain.models.movieimpl.KinopoiskMovieSelectionFiltersImpl

fun KinopoiskMovieSelectionFiltersDto.toDomain() : KinopoiskMovieSelectionFiltersImpl {
    return KinopoiskMovieSelectionFiltersImpl (
        countries = this@toDomain.countries.map { it.toDomain() },
        genres = this@toDomain.genres.map { it.toDomain() }
    )
}