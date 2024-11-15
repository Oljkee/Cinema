package com.example.lvl1final.domain.repository

import com.example.lvl1final.domain.models.movieimpl.ActorImpl
import com.example.lvl1final.domain.models.movieimpl.ImageImpl
import com.example.lvl1final.domain.models.movieimpl.KinopoiskMovieInfoImpl
import com.example.lvl1final.domain.models.movieimpl.KinopoiskMovieSelectionFiltersImpl
import com.example.lvl1final.domain.models.movieimpl.KinopoiskPremieresImpl
import com.example.lvl1final.domain.models.movieimpl.KinopoiskSelectionMoviesImpl
import com.example.lvl1final.domain.models.movieimpl.MovieImpl
import com.example.lvl1final.domain.models.movieimpl.MovieStaffImpl
import com.example.lvl1final.domain.models.movieimpl.SeasonsAndEpisodesImpl
import com.example.lvl1final.domain.models.movieimpl.SimilarMoviesItemImpl

interface MovieRepository {
    suspend fun getPremieres(year: Int, month: String): KinopoiskPremieresImpl
    suspend fun getTopMovies(page: Int, type: String): List<MovieImpl>
    suspend fun getMovieSelectionFilters(): KinopoiskMovieSelectionFiltersImpl
    suspend fun getSelectionMovies(
        countries: Int,
        genres: Int,
        page: Int
    ): KinopoiskSelectionMoviesImpl
    suspend fun getSeries(page: Int): List<MovieImpl>
    suspend fun getMovieInfo(kinopoiskId: Int): KinopoiskMovieInfoImpl
    suspend fun getMovieStaffInfo(filmId: Int): List<MovieStaffImpl>
    suspend fun getMovieImages(id: Int, type: String): List<ImageImpl>
    suspend fun getSimilarMovies(id: Int): List<SimilarMoviesItemImpl>
    suspend fun getSeasonsAndEpisodes(id: Int): SeasonsAndEpisodesImpl
    suspend fun getActorInfo(id: Int): ActorImpl
    suspend fun searchMovie(
        type: String,
        countries: Int?,
        genres: Int?,
        yearFrom: Int,
        yearTo: Int,
        ratingFrom: Int,
        ratingTo: Int,
        order: String,
        keyword: String,
        page: Int
    ): List<MovieImpl>
}