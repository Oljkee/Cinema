package com.example.lvl1final.data.repositories

import com.example.lvl1final.data.api.ActorInfoDto
import com.example.lvl1final.data.api.ImageDto
import com.example.lvl1final.data.api.KinopoiskMovieInfoDto
import com.example.lvl1final.data.api.KinopoiskMovieSelectionFiltersDto
import com.example.lvl1final.data.api.KinopoiskSelectionMoviesDto
import com.example.lvl1final.data.api.MovieDto
import com.example.lvl1final.data.api.MovieStaffDto
import com.example.lvl1final.data.api.SeasonsAndEpisodesDto
import com.example.lvl1final.data.api.SimilarMoviesItemDto

interface KinopoiskNetworkRepository {
    suspend fun getPremieres(): List<MovieDto>
    suspend fun getTopMovies(page: Int, type: String): List<MovieDto>
    suspend fun getMovieSelectionFilters(): KinopoiskMovieSelectionFiltersDto
    suspend fun getSelectionMovies(
        countries: Int,
        genres: Int,
        page: Int
    ): KinopoiskSelectionMoviesDto
    suspend fun getSeries(page: Int): List<MovieDto>
    suspend fun getMovieInfo(kinopoiskId: Int): KinopoiskMovieInfoDto
    suspend fun getMovieStaffInfo(filmId: Int): List<MovieStaffDto>
    suspend fun getMovieImages(id: Int, type: String): List<ImageDto>
    suspend fun getSimilarMovies(id: Int): List<SimilarMoviesItemDto>
    suspend fun getSeasonsAndEpisodes(id: Int): SeasonsAndEpisodesDto
    suspend fun getActorInfo(id: Int): ActorInfoDto
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
    ): List<MovieDto>
}