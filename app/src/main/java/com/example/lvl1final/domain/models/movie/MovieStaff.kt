package com.example.lvl1final.domain.models.movie

interface MovieStaff {
    val description: String?
    val nameEn: String?
    val nameRu: String?
    val posterUrl: String
    val professionKey: String
    val professionText: String
    val staffId: Int
}