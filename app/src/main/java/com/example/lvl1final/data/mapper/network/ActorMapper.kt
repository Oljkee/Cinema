package com.example.lvl1final.data.mapper.network

import com.example.lvl1final.data.network.api.ActorDto
import com.example.lvl1final.data.network.api.FilmDto
import com.example.lvl1final.data.network.api.SpouseDto
import com.example.lvl1final.domain.models.movieimpl.ActorImpl
import com.example.lvl1final.domain.models.movieimpl.FilmImpl
import com.example.lvl1final.domain.models.movieimpl.SpouseImpl

fun ActorDto.toDomain(): ActorImpl {
    return ActorImpl(
        age = this@toDomain.age,
        birthday = this@toDomain.birthday,
        birthplace = this@toDomain.birthplace,
        death = this@toDomain.death,
        deathplace = this@toDomain.deathplace,
        facts = this@toDomain.facts,
        films = this@toDomain.films.map { it.toDomain() },
        growth = this@toDomain.growth,
        hasAwards = this@toDomain.hasAwards,
        nameEn = this@toDomain.nameEn,
        nameRu = this@toDomain.nameRu,
        personId = this@toDomain.personId,
        posterUrl = this@toDomain.posterUrl,
        profession = this@toDomain.profession,
        sex = this@toDomain.sex,
        spouses = this@toDomain.spouses.map { it.toDomain() },
        webUrl = this@toDomain.webUrl
    )
}

fun FilmDto.toDomain(): FilmImpl {
    return FilmImpl(
        description = this@toDomain.description,
        filmId = this@toDomain.filmId,
        general = this@toDomain.general,
        nameEn = this@toDomain.nameEn,
        nameRu = this@toDomain.nameRu,
        professionKey = this@toDomain.professionKey,
        rating = this@toDomain.rating
    )
}

fun SpouseDto.toDomain(): SpouseImpl {
    return SpouseImpl(
        children = this@toDomain.children,
        divorced = this@toDomain.divorced,
        divorcedReason = this@toDomain.divorcedReason,
        name = this@toDomain.name,
        personId = this@toDomain.personId,
        relation = this@toDomain.relation,
        sex = this@toDomain.sex,
        webUrl = this@toDomain.webUrl
    )
}