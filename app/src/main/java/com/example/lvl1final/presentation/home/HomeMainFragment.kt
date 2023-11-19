package com.example.lvl1final.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.lvl1final.databinding.FragmentHomeMainBinding
import com.example.lvl1final.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lvl1final.R
import com.example.lvl1final.data.api.MovieDto
import com.example.lvl1final.data.entity.WatchedMovieWithKinopoiskMovie
import com.example.lvl1final.presentation.Arguments
import com.example.lvl1final.presentation.common.MovieListAdapter
import com.example.lvl1final.presentation.common.MoviePagedListAdapter

@AndroidEntryPoint
class HomeMainFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentHomeMainBinding? = null
    private val binding get() = _binding!!
    private val bundle = Bundle()
    private var watchedMovieList = emptyList<WatchedMovieWithKinopoiskMovie?>()
    private val premiereMovieListAdapter = MovieListAdapter(
        onItemClick = { movie -> onItemClick(movie) },
        isWatchedMovie = { id -> isWatchedMovie(id) }
    )
    private lateinit var showAllPremieresAdapter: ShowAllButtonAdapter

    private val popularMoviePagedListAdapter = MoviePagedListAdapter(
        onItemClick = { movie -> onItemClick(movie) },
        isWatchedMovie = { id -> isWatchedMovie(id) }
    )
    private lateinit var showAllPopularMoviesAdapter: ShowAllButtonAdapter

    private val top250MoviePagedListAdapter = MoviePagedListAdapter(
        onItemClick = { movie -> onItemClick(movie) },
        isWatchedMovie = { id -> isWatchedMovie(id) }
    )
    private lateinit var showAllTop250MoviesAdapter: ShowAllButtonAdapter

    private val firstSelectionMoviePagedListAdapter = MoviePagedListAdapter(
        onItemClick = { movie -> onItemClick(movie) },
        isWatchedMovie = { id -> isWatchedMovie(id) }
    )
    private lateinit var showAllFirstSelectionMoviesAdapter: ShowAllButtonAdapter

    private val secondSelectionMoviePagedListAdapter = MoviePagedListAdapter(
        onItemClick = { movie -> onItemClick(movie) },
        isWatchedMovie = { id -> isWatchedMovie(id) }
    )
    private lateinit var showAllSecondSelectionMoviePsAdapter: ShowAllButtonAdapter

    private val seriesPagedListAdapter = MoviePagedListAdapter(
        onItemClick = { movie -> onItemClick(movie) },
        isWatchedMovie = { id -> isWatchedMovie(id) }
    )
    private lateinit var showAllSeriesAdapter: ShowAllButtonAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.watchedCollectionMovieList.collect { list ->
                    if (list.isNotEmpty()) {
                        watchedMovieList = list
                    }
                }
            }


            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.premieres.collect { movieList ->
                    if (movieList.size > 20) {
                        val displayList = movieList.subList(0, 20)
                        showAllPremieresAdapter = ShowAllButtonAdapter {
                            allMovies(
                                Arguments.TOP_PREMIERES,
                                textviewPremieres.text.toString()
                            )
                        }
                        val concatAdapter = ConcatAdapter(premiereMovieListAdapter, showAllPremieresAdapter)
                        recyclerViewPremieres.adapter = concatAdapter
                        premiereMovieListAdapter.submitList(displayList)
                    } else {
                        recyclerViewPremieres.adapter = premiereMovieListAdapter
                        premiereMovieListAdapter.submitList(movieList)
                    }
                    recyclerViewPremieres.scrollToPosition(0)
                }
            }
            allPremieres.setOnClickListener {
                allMovies(Arguments.TOP_PREMIERES, textviewPremieres.text.toString())
            }
            premiereMovieListAdapter.registerAdapterDataObserver(object :
                RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    recyclerViewPremieres.scrollToPosition(0)
                }
            })

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.previewPagedPopularMovies.collect { movieList ->
                    showAllPopularMoviesAdapter = ShowAllButtonAdapter {
                        allMovies(Arguments.TOP_POPULAR, textviewPopular.text.toString())
                    }
                    val concatAdapter =
                        ConcatAdapter(popularMoviePagedListAdapter, showAllPopularMoviesAdapter)
                    recyclerViewPopular.adapter = concatAdapter
                    popularMoviePagedListAdapter.submitData(movieList)
                }
            }
            allPopular.setOnClickListener {
                allMovies(Arguments.TOP_POPULAR, getString(R.string.popular))
            }
            popularMoviePagedListAdapter.registerAdapterDataObserver(object :
                RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    recyclerViewPopular.scrollToPosition(0)
                }
            })

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.firstSelectionMoviesFiltersLoaded.collectLatest { firstSelectionMoviesFiltersLoaded ->
                    if (firstSelectionMoviesFiltersLoaded) {
                        viewLifecycleOwner.lifecycleScope.launch {
                            recyclerViewFirstSelection.adapter =
                                firstSelectionMoviePagedListAdapter
                            viewModel.previewFirstSelectionMovies.collect { movieList ->
                                showAllFirstSelectionMoviesAdapter = ShowAllButtonAdapter {
                                    allMovies(
                                        Arguments.ARG_FIRST_SELECTION,
                                        textviewFirstSelection.text.toString()
                                    )
                                }
                                val concatAdapter = ConcatAdapter(
                                    firstSelectionMoviePagedListAdapter,
                                    showAllFirstSelectionMoviesAdapter
                                )
                                recyclerViewFirstSelection.adapter = concatAdapter
                                firstSelectionMoviePagedListAdapter.submitData(movieList)
                            }
                        }
                        val genre =
                            viewModel.firstSelectionGenre.value?.genre!!.replaceFirstChar { it.uppercase() }
                        val country =
                            viewModel.firstSelectionCountry.value?.country!!.replaceFirstChar { it.uppercase() }
                        textviewFirstSelection.text = getString(
                            R.string.selection_movies_name,
                            genre,
                            country
                        )
                    }
                }
            }
            allFirstSelection.setOnClickListener {
                allMovies(
                    Arguments.ARG_FIRST_SELECTION,
                    textviewFirstSelection.text.toString()
                )
            }
            firstSelectionMoviePagedListAdapter.registerAdapterDataObserver(object :
                RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    recyclerViewFirstSelection.scrollToPosition(0)
                }
            })


            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.secondSelectionMoviesFiltersLoaded.collect { secondSelectionMoviesFiltersLoaded ->
                    if (secondSelectionMoviesFiltersLoaded) {
                        viewLifecycleOwner.lifecycleScope.launch {
                            recyclerViewSecondSelection.adapter =
                                secondSelectionMoviePagedListAdapter
                            viewModel.previewSecondSelectionMovies.collectLatest { movieList ->
                                showAllSecondSelectionMoviePsAdapter = ShowAllButtonAdapter {
                                    allMovies(
                                        Arguments.ARG_SECOND_SELECTION,
                                        textviewSecondSelection.text.toString()
                                    )
                                }
                                val concatAdapter = ConcatAdapter(
                                    secondSelectionMoviePagedListAdapter,
                                    showAllSecondSelectionMoviePsAdapter
                                )
                                recyclerViewSecondSelection.adapter = concatAdapter
                                secondSelectionMoviePagedListAdapter.submitData(movieList)
                            }
                        }
                        val genre =
                            viewModel.secondSelectionGenre.value?.genre!!.replaceFirstChar { it.uppercase() }
                        val country =
                            viewModel.secondSelectionCountry.value?.country!!.replaceFirstChar { it.uppercase() }
                        textviewSecondSelection.text = getString(
                            R.string.selection_movies_name,
                            genre,
                            country
                        )
                    }
                }
            }
            allSecondSelection.setOnClickListener {
                allMovies(
                    Arguments.ARG_SECOND_SELECTION,
                    textviewSecondSelection.text.toString()
                )
            }
            secondSelectionMoviePagedListAdapter.registerAdapterDataObserver(object :
                RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    recyclerViewSecondSelection.scrollToPosition(0)
                }
            })


            viewLifecycleOwner.lifecycleScope.launch {
                recyclerViewTop250.adapter = top250MoviePagedListAdapter
                viewModel.previewPagedTop250Movies.collect { movieList ->
                    showAllTop250MoviesAdapter = ShowAllButtonAdapter {
                        allMovies(Arguments.TOP_250, textviewTop250.text.toString())
                    }
                    val concatAdapter = ConcatAdapter(
                        top250MoviePagedListAdapter,
                        showAllTop250MoviesAdapter
                    )
                    recyclerViewTop250.adapter = concatAdapter
                    top250MoviePagedListAdapter.submitData(movieList)
                }
            }
            allTop250.setOnClickListener {
                allMovies(Arguments.TOP_250, textviewTop250.text.toString())
            }
            top250MoviePagedListAdapter.registerAdapterDataObserver(object :
                RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    recyclerViewTop250.scrollToPosition(0)
                }
            })

            viewLifecycleOwner.lifecycleScope.launch {
                recyclerViewSeries.adapter = seriesPagedListAdapter
                viewModel.previewPagedTvSeries.collect { movieList ->
                    showAllSeriesAdapter = ShowAllButtonAdapter {
                        allMovies(Arguments.ARG_SERIES_TYPE, textviewSeries.text.toString())
                    }
                    val concatAdapter = ConcatAdapter(seriesPagedListAdapter, showAllSeriesAdapter)
                    recyclerViewSeries.adapter = concatAdapter
                    seriesPagedListAdapter.submitData(movieList)
                }
            }
            allSeries.setOnClickListener {
                allMovies(Arguments.ARG_SERIES_TYPE, textviewSeries.text.toString())
            }
            seriesPagedListAdapter.registerAdapterDataObserver(object :
                RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    recyclerViewSeries.scrollToPosition(0)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClick(movie: MovieDto) {
        val id = if (movie.filmId == null && movie.kinopoiskId != null) {
            movie.kinopoiskId
        } else movie.filmId!!
        viewModel.getMovieData(id)
        bundle.putInt(Arguments.ARG_KINOPOISK_ID, id)
        findNavController().navigate(R.id.movie_info_nested_graph)
    }

    private fun isWatchedMovie(id: Int): Boolean {
        if (watchedMovieList.isNotEmpty()) {
            for (movie in watchedMovieList) {
                if (id == movie?.kinopoiskMovie?.kinopoiskId) return true
            }
        }
        return false
    }

    private fun allMovies(type: String, collectionName: String) {
        bundle.putString(Arguments.TYPE, type)
        bundle.putString(Arguments.COLLECTION_NAME, collectionName)
        findNavController().navigate(R.id.action_mainFragment_to_listPageFragment, bundle)
    }
}