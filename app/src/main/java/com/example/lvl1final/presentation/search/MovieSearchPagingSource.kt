package com.example.lvl1final.presentation.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.lvl1final.domain.models.collection.WatchedMovieWithKinopoiskMovie
import com.example.lvl1final.domain.models.movie.FilterParameters
import com.example.lvl1final.domain.models.movieimpl.MovieImpl
import com.example.lvl1final.domain.usecase.SearchMovieUseCase

class MovieSearchPagingSource(
    private val searchMovieUseCase: SearchMovieUseCase,
    private val filterParameters: FilterParameters,
    private val keyword: String,
    private val isEmptyList: (isEmptyList: Boolean) -> Unit,
    private val watchedMovieList: List<WatchedMovieWithKinopoiskMovie?>
) : PagingSource<Int, MovieImpl>() {

    override fun getRefreshKey(state: PagingState<Int, MovieImpl>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieImpl> {
        val pageNumber = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
                if (keyword == "") {
                    emptyList()
                } else {
                    val countries =
                        if (filterParameters.countryId == 54) null else filterParameters.countryId
                    val genres =
                        if (filterParameters.genreId == 25) null else filterParameters.genreId
                    val list = searchMovieUseCase(
                        type = filterParameters.type,
                        countries = countries,
                        genres = genres,
                        yearFrom = filterParameters.yearFrom.toInt(),
                        yearTo = filterParameters.yearTo.toInt(),
                        ratingFrom = filterParameters.ratingFrom.toInt(),
                        ratingTo = filterParameters.ratingTo.toInt(),
                        order = filterParameters.sorting,
                        keyword = keyword,
                        page = pageNumber
                    )
                    if (pageNumber == FIRST_PAGE) {
                        if (list.isEmpty()) isEmptyList(true)
                        else isEmptyList(false)
                    }

                    if (filterParameters.hideWatchedMovies) {
                        if (watchedMovieList.isNotEmpty()) {
                            val watchedMovieIdSet =
                                watchedMovieList.map { it!!.kinopoiskMovie.kinopoiskId }.toSet()
                            list.filter { movie ->
                                movie.kinopoiskId !in watchedMovieIdSet
                            }
                        } else list
                    } else {
                        list
                    }
                }
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else pageNumber + 1
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )

    }

    companion object {
        const val FIRST_PAGE = 1
    }

}