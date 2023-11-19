package com.example.lvl1final.data.repositories

import com.example.lvl1final.data.api.MovieDto
import java.util.Calendar
import com.example.lvl1final.data.api.ActorInfoDto
import com.example.lvl1final.data.api.ImageDto
import com.example.lvl1final.data.api.KinopoiskMovieInfoDto
import com.example.lvl1final.data.api.KinopoiskMovieSelectionFiltersDto
import com.example.lvl1final.data.api.KinopoiskPremieresDto
import com.example.lvl1final.data.api.KinopoiskSelectionMoviesDto
import com.example.lvl1final.data.api.MovieStaffDto
import com.example.lvl1final.data.api.RetrofitServices
import com.example.lvl1final.data.api.SeasonsAndEpisodesDto
import com.example.lvl1final.data.api.SimilarMoviesItemDto
import java.util.Locale
import com.example.lvl1final.presentation.Arguments
import java.text.SimpleDateFormat
import javax.inject.Inject

class KinopoiskNetworkRepositoryImpl @Inject constructor() : KinopoiskNetworkRepository {

    private lateinit var KinopoiskPremieresDto: KinopoiskPremieresDto

    override suspend fun getPremieres(): List<MovieDto> {
        val currentDate = Calendar.getInstance()
        val currentMonth = currentDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
        val currentYear = currentDate.get(Calendar.YEAR)

        val futureDate = Calendar.getInstance()
        futureDate.add(Calendar.WEEK_OF_YEAR, 2)
        val futureMonth = futureDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
        val futureYear = futureDate.get(Calendar.YEAR)

        KinopoiskPremieresDto = if (futureMonth == currentMonth) {
            RetrofitServices.getKinopoiskData.getPremieres(
                year = currentYear,
                month = currentMonth!!
            )
        } else {
            val premieresCurrentMonth = RetrofitServices.getKinopoiskData.getPremieres(
                year = currentYear,
                month = currentMonth!!
            )
            val premieresFutureMonth = RetrofitServices.getKinopoiskData.getPremieres(
                year = futureYear,
                month = futureMonth!!
            )
            KinopoiskPremieresDto(
                premieresCurrentMonth.total + premieresFutureMonth.total,
                premieresCurrentMonth.items + premieresFutureMonth.items
            )
        }

        // 2 weeks
        val filteredList = KinopoiskPremieresDto.items.filter { film ->
            val premiereDate = Calendar.getInstance()
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = dateFormatter.parse(film.premiereRu!!)
            if (date != null) {
                premiereDate.time = date
            }
            premiereDate.after(currentDate) && premiereDate.before(futureDate)
        }

        val sortedList = filteredList.sortedBy { film ->
            film.premiereRu
        }

        return KinopoiskPremieresDto(sortedList.size, sortedList).items
    }

    override suspend fun getTopMovies(page: Int, type: String): List<MovieDto> {
        return RetrofitServices.getKinopoiskData.getTopMovies(page = page, type = type).films
    }

    override suspend fun getMovieSelectionFilters(): KinopoiskMovieSelectionFiltersDto {
        return RetrofitServices.getKinopoiskData.getMovieSelectionFilters()
    }

    override suspend fun getSelectionMovies(
        countries: Int,
        genres: Int,
        page: Int
    ): KinopoiskSelectionMoviesDto {
        return RetrofitServices.getKinopoiskData.getSelectionMovies(
            countries = countries,
            genres = genres,
            page = page
        )
    }

    override suspend fun getSeries(page: Int): List<MovieDto> {
        return RetrofitServices.getKinopoiskData.getSelectionMovies(
            type = Arguments.ARG_SERIES_TYPE,
            page = page
        ).items
    }

    override suspend fun getMovieInfo(kinopoiskId: Int): KinopoiskMovieInfoDto {
        return RetrofitServices.getKinopoiskData.getMovieInfo(kinopoiskId)
    }

    override suspend fun getMovieStaffInfo(filmId: Int): List<MovieStaffDto> {
        return RetrofitServices.getKinopoiskData.getMovieStaffInfo(filmId)
    }

    override suspend fun getMovieImages(id: Int, type: String): List<ImageDto> {
        return RetrofitServices.getKinopoiskData.getMovieImages(id = id, type = type).items
    }

    override suspend fun getSimilarMovies(id: Int): List<SimilarMoviesItemDto> {
        return RetrofitServices.getKinopoiskData.getSimilarMovies(id = id).items
    }

    override suspend fun getSeasonsAndEpisodes(id: Int): SeasonsAndEpisodesDto {
        return RetrofitServices.getKinopoiskData.getSeasonsAndEpisodes(id)
    }

    override suspend fun getActorInfo(id: Int): ActorInfoDto {
        return RetrofitServices.getKinopoiskData.getActorInfo(id)
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
    ): List<MovieDto> {
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
        ).items
    }
}