package com.example.lvl1final.domain.models.movieimpl

import com.example.lvl1final.domain.models.movie.MovieStaff

data class MovieStaffImpl (
    override val description: String?,
    override val nameEn: String?,
    override val nameRu: String?,
    override val posterUrl: String,
    override val professionKey: String,
    override val professionText: String,
    override val staffId: Int
) : MovieStaff