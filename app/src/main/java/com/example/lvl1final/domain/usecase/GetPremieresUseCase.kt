package com.example.lvl1final.domain.usecase

import com.example.lvl1final.domain.models.movieimpl.KinopoiskPremieresImpl
import com.example.lvl1final.domain.models.movieimpl.MovieImpl
import com.example.lvl1final.domain.repository.MovieRepository
import java.text.SimpleDateFormat
import javax.inject.Inject
import java.util.Calendar
import java.util.Locale

class GetPremieresUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    private lateinit var kinopoiskPremieresImpl: KinopoiskPremieresImpl

    suspend operator fun invoke(): List<MovieImpl> {
        val currentDate = Calendar.getInstance()
        val currentMonth = currentDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
        val currentYear = currentDate.get(Calendar.YEAR)

        val futureDate = Calendar.getInstance()
        futureDate.add(Calendar.WEEK_OF_YEAR, 2)
        val futureMonth = futureDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
        val futureYear = futureDate.get(Calendar.YEAR)

        kinopoiskPremieresImpl = if (futureMonth == currentMonth) {
            movieRepository.getPremieres(
                year = currentYear,
                month = currentMonth!!
            )
        } else {
            val premieresCurrentMonth = movieRepository.getPremieres(
                year = currentYear,
                month = currentMonth!!
            )
            val premieresFutureMonth = movieRepository.getPremieres(
                year = futureYear,
                month = futureMonth!!
            )
            KinopoiskPremieresImpl(
                total = premieresCurrentMonth.total + premieresFutureMonth.total,
                items = premieresCurrentMonth.items + premieresFutureMonth.items
            )
        }

        val filteredList = kinopoiskPremieresImpl.items.filter { film ->
            val premiereDate = Calendar.getInstance()
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = dateFormatter.parse(film.premiereRu!!)
            if (date != null) {
                premiereDate.time = date
            }
            premiereDate.after(currentDate) && premiereDate.before(futureDate)
        }

        val resultList = filteredList.sortedBy { film ->
            film.premiereRu
        }
        return resultList
    }
}