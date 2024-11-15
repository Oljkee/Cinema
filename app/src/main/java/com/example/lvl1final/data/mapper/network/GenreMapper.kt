package com.example.lvl1final.data.mapper.network

import com.example.lvl1final.data.network.api.GenreDto
import com.example.lvl1final.domain.models.movieimpl.GenreImpl

fun GenreDto.toDomain() : GenreImpl {
    return GenreImpl (
        genre = this@toDomain.genre,
        id = this@toDomain.id
    )
}