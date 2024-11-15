package com.example.lvl1final.data.mapper.storage

import com.example.lvl1final.data.storage.entity.CollectionWithMoviesEntity
import com.example.lvl1final.domain.models.collection.CollectionWithMovies

fun CollectionWithMoviesEntity.toDomain() : CollectionWithMovies {
    return CollectionWithMovies(
        collection = this@toDomain.collection.toDomain(),
        movies = this@toDomain.movies.map { it.toDomain() }
    )
}