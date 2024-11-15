package com.example.lvl1final.data.mapper.network

import com.example.lvl1final.data.network.api.SimilarMoviesItemDto
import com.example.lvl1final.domain.models.movieimpl.SimilarMoviesItemImpl

fun SimilarMoviesItemDto.toDomain() : SimilarMoviesItemImpl {
    return SimilarMoviesItemImpl(
        filmId = this@toDomain.filmId,
        nameEn = this@toDomain.nameEn,
        nameOriginal = this@toDomain.nameOriginal,
        nameRu = this@toDomain.nameRu,
        posterUrl = this@toDomain.posterUrl,
        posterUrlPreview = this@toDomain.posterUrlPreview,
        relationType = this@toDomain.relationType
    )
}