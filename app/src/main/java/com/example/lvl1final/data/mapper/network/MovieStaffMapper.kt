package com.example.lvl1final.data.mapper.network

import com.example.lvl1final.data.network.api.MovieStaffDto
import com.example.lvl1final.domain.models.movieimpl.MovieStaffImpl

fun MovieStaffDto.toDomain(): MovieStaffImpl {
    return MovieStaffImpl(
        description = this@toDomain.description,
        nameEn = this@toDomain.nameEn,
        nameRu = this@toDomain.nameRu,
        posterUrl = this@toDomain.posterUrl,
        professionKey = this@toDomain.professionKey,
        professionText = this@toDomain.professionText,
        staffId = this@toDomain.staffId
    )
}