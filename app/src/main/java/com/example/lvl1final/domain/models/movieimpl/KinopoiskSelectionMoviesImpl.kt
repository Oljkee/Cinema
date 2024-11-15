package com.example.lvl1final.domain.models.movieimpl

import com.example.lvl1final.domain.models.movie.KinopoiskSelectionMovies

data class KinopoiskSelectionMoviesImpl(
    override val items: List<MovieImpl>,
    override val total: Int,
    override val totalPages: Int
) : KinopoiskSelectionMovies