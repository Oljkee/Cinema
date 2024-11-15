package com.example.lvl1final.domain.models.movie


interface Actor{
    val age: Int?
    val birthday: String?
    val birthplace: String?
    val death: String?
    val deathplace: String?
    val facts: List<String>
    val films: List<Film>
    val growth: Int?
    val hasAwards: Int?
    val nameEn: String?
    val nameRu: String?
    val personId: Int
    val posterUrl: String
    val profession: String?
    val sex: String?
    val spouses: List<Spouse>
    val webUrl: String?
}

interface Film{
    val description: String?
    val filmId: Int
    val general: Boolean
    val nameEn: String?
    val nameRu: String?
    val professionKey: String
    val rating: String?
}

interface Spouse{
    val children: Int
    val divorced: Boolean
    val divorcedReason: String?
    val name: String?
    val personId: Int
    val relation: String
    val sex: String
    val webUrl: String
}