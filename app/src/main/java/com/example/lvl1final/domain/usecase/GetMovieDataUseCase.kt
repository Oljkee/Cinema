package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.movie.FullMovieData
import com.example.lvl1final.domain.models.movieimpl.ImageImpl
import com.example.lvl1final.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class GetMovieDataUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(id: Int, types: List<String>): FullMovieData {
        val movieInfoDeferred = CoroutineScope(Dispatchers.IO).async {
            movieRepository.getMovieInfo(id)
        }
        val staffInfoDeferred =
            CoroutineScope(Dispatchers.IO).async {
                movieRepository.getMovieStaffInfo(
                    id
                )
            }
        val similarMoviesDeferred =
            CoroutineScope(Dispatchers.IO).async { movieRepository.getSimilarMovies(id = id) }
        val movieImagesDeferred = CoroutineScope(Dispatchers.IO).async {
            var result = listOf<ImageImpl>()
            for (type in types) {
                val images = movieRepository.getMovieImages(id = id, type = type)
                if (images.isNotEmpty()) {
                    result = images
                    break
                }
            }
            result
        }
        val seasonsAndEpisodesDeferred =
            CoroutineScope(Dispatchers.IO).async {
                movieRepository.getSeasonsAndEpisodes(
                    id
                )
            }

        return FullMovieData(
            movieInfo = movieInfoDeferred.await(),
            staffInfo = staffInfoDeferred.await(),
            similarMovies = similarMoviesDeferred.await(),
            movieImages = movieImagesDeferred.await(),
            seasonsAndEpisodes = seasonsAndEpisodesDeferred.await()
        )
    }

}