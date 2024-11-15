package com.example.lvl1final.domain.models.movieimpl

import com.example.lvl1final.domain.models.movie.KinopoiskTop

data class KinopoiskTopImpl(
    override val films: List<MovieImpl>,
    override val pagesCount: Int
) : KinopoiskTop