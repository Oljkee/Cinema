package com.example.lvl1final.domain.models.movie

import com.example.lvl1final.domain.models.movieimpl.ImageImpl
import com.example.lvl1final.domain.models.movieimpl.MovieStaffImpl
import com.example.lvl1final.domain.models.movieimpl.SeasonsAndEpisodesImpl
import com.example.lvl1final.domain.models.movieimpl.SimilarMoviesItemImpl

data class FullMovieData(
    val movieInfo: KinopoiskMovieInfo?,
    val staffInfo: List<MovieStaffImpl>?,
    val similarMovies: List<SimilarMoviesItemImpl>,
    val movieImages: List<ImageImpl>?,
    val seasonsAndEpisodes: SeasonsAndEpisodesImpl?
)
