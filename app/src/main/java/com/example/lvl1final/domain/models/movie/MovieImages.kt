package com.example.lvl1final.domain.models.movie

interface MovieImages {
    val items: List<Image>
    val total: Int
    val totalPages: Int
}

interface Image {
    val imageUrl: String
    val previewUrl: String
}