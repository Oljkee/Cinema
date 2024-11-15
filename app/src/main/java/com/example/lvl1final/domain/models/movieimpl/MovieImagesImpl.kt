package com.example.lvl1final.domain.models.movieimpl

import com.example.lvl1final.domain.models.movie.Image
import com.example.lvl1final.domain.models.movie.MovieImages

data class MovieImagesImpl (
    override val items: List<ImageImpl>,
    override val total: Int,
    override val totalPages: Int
) : MovieImages


data class ImageImpl (
    override val imageUrl: String,
    override val previewUrl: String
) : Image