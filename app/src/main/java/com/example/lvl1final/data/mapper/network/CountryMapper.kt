package com.example.lvl1final.data.mapper.network

import com.example.lvl1final.data.network.api.CountryDto
import com.example.lvl1final.domain.models.movieimpl.CountryImpl

fun CountryDto.toDomain() : CountryImpl {
    return CountryImpl (
        country = this@toDomain.country,
        id = this@toDomain.id
    )
}