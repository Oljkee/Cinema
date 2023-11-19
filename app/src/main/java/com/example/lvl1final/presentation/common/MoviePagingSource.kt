package com.example.lvl1final.presentation.common

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.lvl1final.data.api.CountryDto
import com.example.lvl1final.data.api.GenreDto
import com.example.lvl1final.data.api.MovieDto
import com.example.lvl1final.domain.GetSelectionMoviesUseCase
import com.example.lvl1final.domain.GetSeriesUseCase
import com.example.lvl1final.domain.GetTopMoviesUseCase
import com.example.lvl1final.presentation.Arguments
import kotlin.random.Random

class MoviePagingSource(
    private val getSeriesUseCase: GetSeriesUseCase,
    private val getTopMoviesUseCase: GetTopMoviesUseCase,
    private val getSelectionMoviesUseCase: GetSelectionMoviesUseCase,
    private val listType: String,
    private val countries: CountryDto? = null,
    private val genres: GenreDto? = null,
    private val listPreview: Boolean = false
) : PagingSource<Int, MovieDto>() {
    private var counter = 0

    override fun getRefreshKey(state: PagingState<Int, MovieDto>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
        val pageNumber = params.key ?: FIRST_PAGE
        var movieList: List<MovieDto>

        return kotlin.runCatching {
            if (listPreview) {
                if (counter > 0) {
                    return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
                }

                movieList = when (listType) {
                    Arguments.TOP_POPULAR -> getTopMoviesUseCase(
                        pageNumber,
                        Arguments.TOP_POPULAR
                    )

                    Arguments.TOP_250 -> getTopMoviesUseCase(
                        Random.nextInt(0, 13),
                        Arguments.TOP_250
                    )

                    Arguments.ARG_FILM_TYPE -> {
                        getSelectionMoviesUseCase(
                            countries = countries?.id!!,
                            genres = genres?.id!!,
                            page = FIRST_PAGE
                        ).items
                    }

                    else -> {
                        getSeriesUseCase(Random.nextInt(0, 5))
                    }
                }
                counter++
            } else {
                movieList = when (listType) {
                    Arguments.TOP_POPULAR -> getTopMoviesUseCase(
                        pageNumber,
                        Arguments.TOP_POPULAR
                    )

                    Arguments.TOP_250 -> getTopMoviesUseCase(
                        pageNumber,
                        Arguments.TOP_250
                    )

                    Arguments.ARG_FILM_TYPE -> {
                        getSelectionMoviesUseCase(
                            countries = countries?.id!!,
                            genres = genres?.id!!,
                            page = pageNumber
                        ).items
                    }

                    else -> {
                        getSeriesUseCase(pageNumber)
                    }
                }
            }

            movieList
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