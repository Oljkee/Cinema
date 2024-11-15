package com.example.lvl1final.domain.models.movieimpl

import com.example.lvl1final.domain.models.movie.Genre

data class GenreImpl (
    override val genre: String,
    override val id: Int?
) : Genre