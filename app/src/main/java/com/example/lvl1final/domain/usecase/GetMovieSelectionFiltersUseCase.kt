package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.movieimpl.CountryImpl
import com.example.lvl1final.domain.models.movieimpl.GenreImpl
import com.example.lvl1final.domain.models.movieimpl.KinopoiskMovieSelectionFiltersImpl
import com.example.lvl1final.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class GetMovieSelectionFiltersUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): List<Pair<CountryImpl?, GenreImpl?>> {
        val result = mutableListOf<Pair<CountryImpl, GenreImpl>>()
        val countriesAndGenres = movieRepository.getMovieSelectionFilters()

        val operation1 = CoroutineScope(Dispatchers.IO).async {
            val countryGenrePair = getCountryGenrePair(countriesAndGenres)
            result.add(countryGenrePair)
        }

        val operation2 = CoroutineScope(Dispatchers.IO).async {
            val countryGenrePair = getCountryGenrePair(countriesAndGenres)
            result.add(countryGenrePair)
        }

        operation1.await()
        operation2.await()

        return result
    }

    private suspend fun getCountryGenrePair(countriesAndGenres: KinopoiskMovieSelectionFiltersImpl): Pair<CountryImpl, GenreImpl> {
        do {
            val country = countriesAndGenres.countries.random()
            val genre = countriesAndGenres.genres.random()
            val kinopoiskSelectionMoviesDto = movieRepository.getSelectionMovies(
                country.id!!,
                genre.id!!,
                page = 1
            )
            if (kinopoiskSelectionMoviesDto.total > 0) {
                return Pair(country, genre)
            }
        } while (true)
    }
}