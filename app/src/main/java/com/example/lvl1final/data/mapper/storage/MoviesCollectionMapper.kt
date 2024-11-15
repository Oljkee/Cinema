package com.example.lvl1final.data.mapper.storage

import com.example.lvl1final.data.storage.entity.MoviesCollectionEntity
import com.example.lvl1final.domain.models.collection.MoviesCollection

fun MoviesCollectionEntity.toDomain() : MoviesCollection {
    return MoviesCollection(
        id = this@toDomain.id,
        collectionName = this@toDomain.collectionName,
        collectionIcon = this@toDomain.collectionIcon,
        numberOfMovies = this@toDomain.numberOfMovies,
        deletable = this@toDomain.deletable
    )
}

fun MoviesCollection.toEntity() : MoviesCollectionEntity {
    return MoviesCollectionEntity(
        id = this.id,
        collectionName = this.collectionName,
        collectionIcon = this.collectionIcon,
        numberOfMovies = this.numberOfMovies,
        deletable = this.deletable
    )
}

fun List<MoviesCollectionEntity>.toDomainList(): List<MoviesCollection> {
    return this.filterNotNull().map { it.toDomain() }
}