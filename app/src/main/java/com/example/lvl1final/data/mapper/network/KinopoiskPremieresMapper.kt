package com.example.lvl1final.data.mapper.network

import com.example.lvl1final.data.network.api.KinopoiskPremieresDto
import com.example.lvl1final.domain.models.movieimpl.KinopoiskPremieresImpl

fun KinopoiskPremieresDto.toDomain(): KinopoiskPremieresImpl {
    return KinopoiskPremieresImpl(
        total = this@toDomain.total,
        items = this@toDomain.items.map { it.toDomain() }
    )
}