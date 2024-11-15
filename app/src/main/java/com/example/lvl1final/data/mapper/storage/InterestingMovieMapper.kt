package com.example.lvl1final.data.mapper.storage

import com.example.lvl1final.data.storage.entity.InterestingMovieEntity
import com.example.lvl1final.domain.models.collection.InterestingMovie

fun InterestingMovieEntity.toDomain() : InterestingMovie {
    return InterestingMovie(
        id = this@toDomain.id,
        kinopoiskId = this@toDomain.kinopoiskId
    )
}

fun InterestingMovie.toEntity(): InterestingMovieEntity {
    return InterestingMovieEntity(
        id = this.id,
        kinopoiskId = this.kinopoiskId
    )
}