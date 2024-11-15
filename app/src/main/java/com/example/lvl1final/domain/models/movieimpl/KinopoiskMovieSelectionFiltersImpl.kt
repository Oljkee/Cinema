package com.example.lvl1final.domain.models.movieimpl

import com.example.lvl1final.domain.models.movie.KinopoiskMovieSelectionFilters

data class KinopoiskMovieSelectionFiltersImpl(
    override val countries: List<CountryImpl>,
    override val genres: List<GenreImpl>
) : KinopoiskMovieSelectionFilters