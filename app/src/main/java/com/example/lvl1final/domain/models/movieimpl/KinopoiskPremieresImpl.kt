package com.example.lvl1final.domain.models.movieimpl

import com.example.lvl1final.domain.models.movie.KinopoiskPremieres

data class KinopoiskPremieresImpl (
    override val items: List<MovieImpl>,
    override val total: Int
) : KinopoiskPremieres