package com.example.lvl1final.data.mapper.network

import com.example.lvl1final.data.network.api.ImageDto
import com.example.lvl1final.domain.models.movieimpl.ImageImpl

fun ImageDto.toDomain(): ImageImpl {
    return ImageImpl(
        imageUrl = this@toDomain.imageUrl,
        previewUrl = this@toDomain.previewUrl
    )
}