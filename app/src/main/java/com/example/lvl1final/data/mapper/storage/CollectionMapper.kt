package com.example.lvl1final.data.mapper.storage

import com.example.lvl1final.data.storage.entity.CollectionEntity
import com.example.lvl1final.domain.models.collection.Collection

fun CollectionEntity.toDomain() : Collection {
    return Collection(
        id = this@toDomain.id,
        collectionName = this@toDomain.collectionName,
        numberOfMovies = this@toDomain.numberOfMovies,
        isMovieInCollection = this@toDomain.isMovieInCollection
    )
}