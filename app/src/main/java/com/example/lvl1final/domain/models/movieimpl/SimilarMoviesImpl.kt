package com.example.lvl1final.domain.models.movieimpl

import com.example.lvl1final.domain.models.movie.SimilarMovies
import com.example.lvl1final.domain.models.movie.SimilarMoviesItem

data class SimilarMoviesImpl(
    override val items: List<SimilarMoviesItemImpl>,
    override val total: Int
) : SimilarMovies

data class SimilarMoviesItemImpl(
    override val filmId: Int,
    override val nameEn: String?,
    override val nameOriginal: String?,
    override val nameRu: String?,
    override val posterUrl: String,
    override val posterUrlPreview: String,
    override val relationType: String
) : SimilarMoviesItem