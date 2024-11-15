package com.example.lvl1final.domain.models.movieimpl

import com.example.lvl1final.domain.models.movie.Country

data class CountryImpl(
    override val country: String,
    override val id: Int?
) : Country
