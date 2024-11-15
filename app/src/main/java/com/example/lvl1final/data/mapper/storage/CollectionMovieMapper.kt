package com.example.lvl1final.data.mapper.storage

import com.example.lvl1final.data.storage.entity.CollectionMovieEntity
import com.example.lvl1final.domain.models.collection.CollectionMovie

fun CollectionMovieEntity.toDomain() : CollectionMovie {
    return CollectionMovie(
        collectionId = this@toDomain.collectionId,
        movieId = this@toDomain.movieId
    )
}

fun CollectionMovie.toEntity(): CollectionMovieEntity {
    return CollectionMovieEntity(
        collectionId = this.collectionId,
        movieId = this.movieId
    )
}