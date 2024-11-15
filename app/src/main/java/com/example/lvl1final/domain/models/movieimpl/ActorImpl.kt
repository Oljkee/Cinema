package com.example.lvl1final.domain.models.movieimpl

import com.example.lvl1final.domain.models.movie.Actor
import com.example.lvl1final.domain.models.movie.Film
import com.example.lvl1final.domain.models.movie.Spouse

data class ActorImpl(
    override val age: Int?,
    override val birthday: String?,
    override val birthplace: String?,
    override val death: String?,
    override val deathplace: String?,
    override val facts: List<String>,
    override val films: List<FilmImpl>,
    override val growth: Int?,
    override val hasAwards: Int?,
    override val nameEn: String?,
    override val nameRu: String?,
    override val personId: Int,
    override val posterUrl: String,
    override val profession: String?,
    override val sex: String?,
    override val spouses: List<SpouseImpl>,
    override val webUrl: String?
) : Actor

data class FilmImpl(
    override val description: String?,
    override val filmId: Int,
    override val general: Boolean,
    override val nameEn: String?,
    override val nameRu: String?,
    override val professionKey: String,
    override val rating: String?
) : Film

data class SpouseImpl(
    override val children: Int,
    override val divorced: Boolean,
    override val divorcedReason: String?,
    override val name: String?,
    override val personId: Int,
    override val relation: String,
    override val sex: String,
    override val webUrl: String
) : Spouse