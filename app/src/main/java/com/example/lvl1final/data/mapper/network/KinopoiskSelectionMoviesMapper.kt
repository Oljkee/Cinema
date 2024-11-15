package com.example.lvl1final.data.mapper.network

import com.example.lvl1final.data.network.api.KinopoiskSelectionMoviesDto
import com.example.lvl1final.domain.models.movieimpl.KinopoiskSelectionMoviesImpl

fun KinopoiskSelectionMoviesDto.toDomain() : KinopoiskSelectionMoviesImpl {
    return KinopoiskSelectionMoviesImpl (
        items = this@toDomain.items.map { it.toDomain() },
        total = this@toDomain.total,
        totalPages = this@toDomain.totalPages
    )
}