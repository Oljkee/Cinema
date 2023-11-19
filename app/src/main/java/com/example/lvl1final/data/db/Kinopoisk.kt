package com.example.lvl1final.data.db

interface KinopoiskPremieres {
    val items: List<Movie>
    val total: Int
}

interface KinopoiskTop {
    val films: List<Movie>
    val pagesCount: Int
}

interface KinopoiskSelectionMovies {
    val items: List<Movie>
    val total: Int
    val totalPages: Int
}

interface Movie {
    val duration: Int?
    val premiereRu: String?

    val countries: List<Country>
    val filmId: Int?
    val filmLength: String?
    val genres: List<Genre>
    val nameEn: String?
    val nameRu: String?
    val posterUrl: String
    val posterUrlPreview: String
    val rating: String?
    val ratingChange: Any?
    val ratingVoteCount: Int?
    val year: Int?

    val imdbId: String?
    val kinopoiskId: Int?
    val nameOriginal: String?
    val ratingImdb: Double?
    val ratingKinopoisk: Double?
    val type: String?
}

interface KinopoiskMovieSelectionFilters {
    val countries: List<Country>
    val genres: List<Genre>
}

interface KinopoiskMovieInfo {
    val completed: Boolean?
    val countries: List<Country>
    val coverUrl: String?
    val description: String?
    val editorAnnotation: String?
    val endYear: Int?
    val filmLength: Int?
    val genres: List<Genre>
    val has3D: Boolean?
    val hasImax: Boolean?
    val imdbId: String?
    val isTicketsAvailable: Boolean
    val kinopoiskId: Int
    val lastSync: String
    val logoUrl: String?
    val nameEn: String?
    val nameOriginal: String?
    val nameRu: String?
    val posterUrl: String
    val posterUrlPreview: String
    val productionStatus: String?
    val ratingAgeLimits: String?
    val ratingAwait: Double?
    val ratingAwaitCount: Int?
    val ratingFilmCritics: Double?
    val ratingFilmCriticsVoteCount: Int?
    val ratingGoodReview: Double?
    val ratingGoodReviewVoteCount: Int?
    val ratingImdb: Double?
    val ratingImdbVoteCount: Int?
    val ratingKinopoisk: Double?
    val ratingKinopoiskVoteCount: Int?
    val ratingMpaa: String?
    val ratingRfCritics: Double?
    val ratingRfCriticsVoteCount: Int?
    val reviewsCount: Int
    val serial: Boolean?
    val shortDescription: String?
    val shortFilm: Boolean?
    val slogan: String?
    val startYear: Int?
    val type: String
    val webUrl: String
    val year: Int?
}

interface MovieStaff {
    val description: String?
    val nameEn: String?
    val nameRu: String?
    val posterUrl: String
    val professionKey: String
    val professionText: String
    val staffId: Int
}

interface Country {
    val country: String
    val id: Int?
}

interface Genre {
    val genre: String
    val id: Int?
}

interface MovieImages {
    val items: List<Image>
    val total: Int
    val totalPages: Int
}

interface Image {
    val imageUrl: String
    val previewUrl: String
}

interface SimilarMovies {
    val items: List<SimilarMoviesItem>
    val total: Int
}

interface SimilarMoviesItem {
    val filmId: Int
    val nameEn: String?
    val nameOriginal: String?
    val nameRu: String?
    val posterUrl: String
    val posterUrlPreview: String
    val relationType: String
}


interface SeasonsAndEpisodes{
    val items: List<Season>
    val total: Int
}

interface Season{
    val episodes: List<Episode>
    val number: Int
}

interface Episode{
    val episodeNumber: Int
    val nameEn: String?
    val nameRu: String?
    val releaseDate: String?
    val seasonNumber: Int
    val synopsis: String?
}

interface ActorInfo{
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


