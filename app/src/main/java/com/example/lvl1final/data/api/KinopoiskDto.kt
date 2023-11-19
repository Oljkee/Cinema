package com.example.lvl1final.data.api

import com.example.lvl1final.data.db.ActorInfo
import com.example.lvl1final.data.db.Country
import com.example.lvl1final.data.db.Episode
import com.example.lvl1final.data.db.Film
import com.example.lvl1final.data.db.Genre
import com.example.lvl1final.data.db.Image
import com.example.lvl1final.data.db.KinopoiskMovieInfo
import com.example.lvl1final.data.db.KinopoiskMovieSelectionFilters
import com.example.lvl1final.data.db.KinopoiskPremieres
import com.example.lvl1final.data.db.KinopoiskSelectionMovies
import com.example.lvl1final.data.db.KinopoiskTop
import com.example.lvl1final.data.db.Movie
import com.example.lvl1final.data.db.MovieImages
import com.example.lvl1final.data.db.MovieStaff
import com.example.lvl1final.data.db.Season
import com.example.lvl1final.data.db.SeasonsAndEpisodes
import com.example.lvl1final.data.db.SimilarMovies
import com.example.lvl1final.data.db.SimilarMoviesItem
import com.example.lvl1final.data.db.Spouse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.inject.Inject

@JsonClass(generateAdapter = true)
data class KinopoiskPremieresDto @Inject constructor(
    @Json(name = "total") override val total: Int,
    @Json(name = "items") override val items: List<MovieDto>
) : KinopoiskPremieres

@JsonClass(generateAdapter = true)
data class KinopoiskTopDto @Inject constructor(
    @Json(name = "pagesCount") override val pagesCount: Int,
    @Json(name = "films") override val films: List<MovieDto>
) : KinopoiskTop

@JsonClass(generateAdapter = true)
data class KinopoiskMovieSelectionFiltersDto @Inject constructor(
    @Json(name = "countries") override val countries: List<CountryDto>,
    @Json(name = "genres") override val genres: List<GenreDto>
) : KinopoiskMovieSelectionFilters

@JsonClass(generateAdapter = true)
data class KinopoiskSelectionMoviesDto @Inject constructor(
    @Json(name = "items") override val items: List<MovieDto>,
    @Json(name = "total") override val total: Int,
    @Json(name = "totalPages") override val totalPages: Int
) : KinopoiskSelectionMovies

@JsonClass(generateAdapter = true)
data class MovieDto(
    @Json(name = "duration") override val duration: Int?,
    @Json(name = "premiereRu") override val premiereRu: String?,
    @Json(name = "countries") override val countries: List<CountryDto>,
    @Json(name = "filmId") override val filmId: Int?,
    @Json(name = "filmLength") override val filmLength: String?,
    @Json(name = "genres") override val genres: List<GenreDto>,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "posterUrl") override val posterUrl: String,
    @Json(name = "posterUrlPreview") override val posterUrlPreview: String,
    @Json(name = "rating") override val rating: String?,
    @Json(name = "ratingChange") override val ratingChange: Any?,
    @Json(name = "ratingVoteCount") override val ratingVoteCount: Int?,
    @Json(name = "year") override val year: Int?,
    @Json(name = "imdbId") override val imdbId: String?,
    @Json(name = "kinopoiskId") override val kinopoiskId: Int?,
    @Json(name = "nameOriginal") override val nameOriginal: String?,
    @Json(name = "ratingImdb") override val ratingImdb: Double?,
    @Json(name = "ratingKinopoisk") override val ratingKinopoisk: Double?,
    @Json(name = "type") override val type: String?
) : Movie

@JsonClass(generateAdapter = true)
data class KinopoiskMovieInfoDto(
    @Json(name = "completed") override val completed: Boolean?,
    @Json(name = "countries") override val countries: List<CountryDto>,
    @Json(name = "coverUrl") override val coverUrl: String?,
    @Json(name = "description") override val description: String?,
    @Json(name = "editorAnnotation") override val editorAnnotation: String?,
    @Json(name = "endYear") override val endYear: Int?,
    @Json(name = "filmLength") override val filmLength: Int?,
    @Json(name = "genres") override val genres: List<GenreDto>,
    @Json(name = "has3D") override val has3D: Boolean?,
    @Json(name = "hasImax") override val hasImax: Boolean?,
    @Json(name = "imdbId") override val imdbId: String?,
    @Json(name = "isTicketsAvailable") override val isTicketsAvailable: Boolean,
    @Json(name = "kinopoiskId") override val kinopoiskId: Int,
    @Json(name = "lastSync") override val lastSync: String,
    @Json(name = "logoUrl") override val logoUrl: String?,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "nameOriginal") override val nameOriginal: String?,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "posterUrl") override val posterUrl: String,
    @Json(name = "posterUrlPreview") override val posterUrlPreview: String,
    @Json(name = "productionStatus") override val productionStatus: String?,
    @Json(name = "ratingAgeLimits") override val ratingAgeLimits: String?,
    @Json(name = "ratingAwait") override val ratingAwait: Double?,
    @Json(name = "ratingAwaitCount") override val ratingAwaitCount: Int?,
    @Json(name = "ratingFilmCritics") override val ratingFilmCritics: Double?,
    @Json(name = "ratingFilmCriticsVoteCount") override val ratingFilmCriticsVoteCount: Int?,
    @Json(name = "ratingGoodReview") override val ratingGoodReview: Double?,
    @Json(name = "ratingGoodReviewVoteCount") override val ratingGoodReviewVoteCount: Int?,
    @Json(name = "ratingImdb") override val ratingImdb: Double?,
    @Json(name = "ratingImdbVoteCount") override val ratingImdbVoteCount: Int?,
    @Json(name = "ratingKinopoisk") override val ratingKinopoisk: Double?,
    @Json(name = "ratingKinopoiskVoteCount") override val ratingKinopoiskVoteCount: Int?,
    @Json(name = "ratingMpaa") override val ratingMpaa: String?,
    @Json(name = "ratingRfCritics") override val ratingRfCritics: Double?,
    @Json(name = "ratingRfCriticsVoteCount") override val ratingRfCriticsVoteCount: Int?,
    @Json(name = "reviewsCount") override val reviewsCount: Int,
    @Json(name = "serial") override val serial: Boolean?,
    @Json(name = "shortDescription") override val shortDescription: String?,
    @Json(name = "shortFilm") override val shortFilm: Boolean?,
    @Json(name = "slogan") override val slogan: String?,
    @Json(name = "startYear") override val startYear: Int?,
    @Json(name = "type") override val type: String,
    @Json(name = "webUrl") override val webUrl: String,
    @Json(name = "year") override val year: Int?
) : KinopoiskMovieInfo

@JsonClass(generateAdapter = true)
data class MovieStaffDto(
    @Json(name = "description") override val description: String?,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "posterUrl") override val posterUrl: String,
    @Json(name = "professionKey") override val professionKey: String,
    @Json(name = "professionText") override val professionText: String,
    @Json(name = "staffId") override val staffId: Int
) : MovieStaff

@JsonClass(generateAdapter = true)
data class MovieImagesDto(
    @Json(name = "items") override val items: List<ImageDto>,
    @Json(name = "total") override val total: Int,
    @Json(name = "totalPages") override val totalPages: Int
) : MovieImages

@JsonClass(generateAdapter = true)
data class ImageDto(
    @Json(name = "imageUrl") override val imageUrl: String,
    @Json(name = "previewUrl") override val previewUrl: String
) : Image

@JsonClass(generateAdapter = true)
data class SimilarMoviesDto(
    @Json(name = "items") override val items: List<SimilarMoviesItemDto>,
    @Json(name = "total") override val total: Int
) : SimilarMovies

@JsonClass(generateAdapter = true)
data class SimilarMoviesItemDto(
    @Json(name = "filmId") override val filmId: Int,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "nameOriginal") override val nameOriginal: String?,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "posterUrl") override val posterUrl: String,
    @Json(name = "posterUrlPreview") override val posterUrlPreview: String,
    @Json(name = "relationType") override val relationType: String
) : SimilarMoviesItem

@JsonClass(generateAdapter = true)
data class CountryDto(
    @Json(name = "country") override val country: String,
    @Json(name = "id") override val id: Int?
) : Country

@JsonClass(generateAdapter = true)
data class GenreDto(
    @Json(name = "genre") override val genre: String,
    @Json(name = "id") override val id: Int?
) : Genre

@JsonClass(generateAdapter = true)
data class SeasonsAndEpisodesDto(
    @Json(name = "items") override val items: List<SeasonDto>,
    @Json(name = "total") override val total: Int
) : SeasonsAndEpisodes

@JsonClass(generateAdapter = true)
data class SeasonDto(
    @Json(name = "episodes") override val episodes: List<EpisodeDto>,
    @Json(name = "number") override val number: Int
) : Season

@JsonClass(generateAdapter = true)
data class EpisodeDto(
    @Json(name = "episodeNumber") override val episodeNumber: Int,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "releaseDate") override val releaseDate: String?,
    @Json(name = "seasonNumber") override val seasonNumber: Int,
    @Json(name = "synopsis") override val synopsis: String?
) : Episode

@JsonClass(generateAdapter = true)
data class ActorInfoDto(
    @Json(name = "age") override val age: Int?,
    @Json(name = "birthday") override val birthday: String?,
    @Json(name = "birthplace") override val birthplace: String?,
    @Json(name = "death") override val death: String?,
    @Json(name = "deathplace") override val deathplace: String?,
    @Json(name = "facts") override val facts: List<String>,
    @Json(name = "films") override val films: List<FilmDto>,
    @Json(name = "growth") override val growth: Int?,
    @Json(name = "hasAwards") override val hasAwards: Int?,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "personId") override val personId: Int,
    @Json(name = "posterUrl") override val posterUrl: String,
    @Json(name = "profession") override val profession: String?,
    @Json(name = "sex") override val sex: String?,
    @Json(name = "spouses") override val spouses: List<SpouseDto>,
    @Json(name = "webUrl") override val webUrl: String?
) : ActorInfo

@JsonClass(generateAdapter = true)
data class FilmDto(
    @Json(name = "description") override val description: String?,
    @Json(name = "filmId") override val filmId: Int,
    @Json(name = "general") override val general: Boolean,
    @Json(name = "nameEn") override val nameEn: String?,
    @Json(name = "nameRu") override val nameRu: String?,
    @Json(name = "professionKey") override val professionKey: String,
    @Json(name = "rating") override val rating: String?
) : Film

@JsonClass(generateAdapter = true)
data class SpouseDto(
    @Json(name = "children") override val children: Int,
    @Json(name = "divorced") override val divorced: Boolean,
    @Json(name = "divorcedReason") override val divorcedReason: String?,
    @Json(name = "name") override val name: String?,
    @Json(name = "personId") override val personId: Int,
    @Json(name = "relation") override val relation: String,
    @Json(name = "sex") override val sex: String,
    @Json(name = "webUrl") override val webUrl: String
) : Spouse