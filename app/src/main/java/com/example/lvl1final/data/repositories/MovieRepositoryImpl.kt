package com.example.lvl1final.data.repositories

import com.example.lvl1final.data.network.api.RetrofitServices
import com.example.lvl1final.data.mapper.network.toDomain
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
import com.example.lvl1final.domain.repository.MovieRepository
import com.example.lvl1final.presentation.Arguments
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor() : MovieRepository {

    override suspend fun getPremieres(year: Int, month: String): KinopoiskPremieresImpl {
        return RetrofitServices.getKinopoiskData.getPremieres(year, month).toDomain()
    }

    override suspend fun getTopMovies(page: Int, type: String): List<MovieImpl> {
        return RetrofitServices.getKinopoiskData.getTopMovies(
            page = page,
            type = type
        ).films.map { dto ->
            dto.toDomain()
        }
    }

    override suspend fun getMovieSelectionFilters(): KinopoiskMovieSelectionFiltersImpl {
        return RetrofitServices.getKinopoiskData.getMovieSelectionFilters().toDomain()
    }

    override suspend fun getSelectionMovies(
        countries: Int,
        genres: Int,
        page: Int
    ): KinopoiskSelectionMoviesImpl {
        return RetrofitServices.getKinopoiskData.getSelectionMovies(
            countries = countries,
            genres = genres,
            page = page
        ).toDomain()
    }

    override suspend fun getSeries(page: Int): List<MovieImpl> {
        return RetrofitServices.getKinopoiskData.getSelectionMovies(
            type = Arguments.ARG_SERIES_TYPE,
            page = page
        ).items.map { dto ->
            dto.toDomain()
        }
    }

    override suspend fun getMovieInfo(kinopoiskId: Int): KinopoiskMovieInfoImpl {
        return RetrofitServices.getKinopoiskData.getMovieInfo(kinopoiskId).toDomain()
    }

    override suspend fun getMovieStaffInfo(filmId: Int): List<MovieStaffImpl> {
        return RetrofitServices.getKinopoiskData.getMovieStaffInfo(filmId).map { dto ->
            dto.toDomain()
        }
    }

    override suspend fun getMovieImages(id: Int, type: String): List<ImageImpl> {
        return RetrofitServices.getKinopoiskData.getMovieImages(
            id = id,
            type = type
        ).items.map { dto ->
            dto.toDomain()
        }
    }

    override suspend fun getSimilarMovies(id: Int): List<SimilarMoviesItemImpl> {
        return RetrofitServices.getKinopoiskData.getSimilarMovies(id = id).items.map { dto ->
            dto.toDomain()
        }
    }

    override suspend fun getSeasonsAndEpisodes(id: Int): SeasonsAndEpisodesImpl {
        return RetrofitServices.getKinopoiskData.getSeasonsAndEpisodes(id).toDomain()
    }

    override suspend fun getActorInfo(id: Int): ActorImpl {
        return RetrofitServices.getKinopoiskData.getActorInfo(id).toDomain()
    }

    override suspend fun searchMovie(
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
    ): List<MovieImpl> {
        return RetrofitServices.getKinopoiskData.getSelectionMovies(
            type = type,
            countries = countries,
            genres = genres,
            yearFrom = yearFrom,
            yearTo = yearTo,
            ratingFrom = ratingFrom,
            ratingTo = ratingTo,
            order = order,
            keyword = keyword,
            page = page
        ).items.map { dto ->
            dto.toDomain()
        }
    }
}