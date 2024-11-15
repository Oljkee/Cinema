package com.example.lvl1final.data.mapper.storage

import com.example.lvl1final.data.storage.entity.InterestingMovieWithKinopoiskMovieEntity
import com.example.lvl1final.domain.models.collection.InterestingMovieWithKinopoiskMovie

fun InterestingMovieWithKinopoiskMovieEntity.toDomain() : InterestingMovieWithKinopoiskMovie {
    return InterestingMovieWithKinopoiskMovie(
        interestingMovie  = this@toDomain.interestingMovieEntity.toDomain(),
        kinopoiskMovie  = this@toDomain.kinopoiskMovieEntity.toDomain()
    )
}

fun List<InterestingMovieWithKinopoiskMovieEntity?>.toDomainList(): List<InterestingMovieWithKinopoiskMovie?> {
    return this.filterNotNull().map { it.toDomain() }
}