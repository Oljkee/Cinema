package com.example.lvl1final.presentation.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lvl1final.R
import com.example.lvl1final.data.api.MovieDto
import com.example.lvl1final.data.api.SimilarMoviesItemDto
import com.example.lvl1final.databinding.FragmentListPageBinding
import com.example.lvl1final.data.entity.KinopoiskMovie
import com.example.lvl1final.data.entity.WatchedMovieWithKinopoiskMovie
import com.example.lvl1final.presentation.Arguments
import com.example.lvl1final.presentation.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListPageFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentListPageBinding? = null
    private val binding get() = _binding!!
    private var watchedMovieList = emptyList<WatchedMovieWithKinopoiskMovie?>()
    private val movieListAdapter = MovieListAdapter(
        onItemClick = { movie -> onItemClick(movie) },
        isWatchedMovie = { id -> isWatchedMovie(id) }
    )

    private val popularMoviePagedListAdapter = MoviePagedListAdapter(
        onItemClick = { movie -> onItemClick(movie) },
        isWatchedMovie = { id -> isWatchedMovie(id) }
    )
    private val top250MoviePagedListAdapter = MoviePagedListAdapter(
        onItemClick = { movie -> onItemClick(movie) },
        isWatchedMovie = { id -> isWatchedMovie(id) }
    )
    private val firstSelectionMoviePagedListAdapter = MoviePagedListAdapter(
        onItemClick = { movie -> onItemClick(movie) },
        isWatchedMovie = { id -> isWatchedMovie(id) }
    )
    private val secondSelectionMoviePagedListAdapter = MoviePagedListAdapter(
        onItemClick = { movie -> onItemClick(movie) },
        isWatchedMovie = { id -> isWatchedMovie(id) }
    )
    private val seriesPagedListAdapter = MoviePagedListAdapter(
        onItemClick = { movie -> onItemClick(movie) },
        isWatchedMovie = { id -> isWatchedMovie(id) }
    )
    private val similarMovieListAdapter = SimilarMovieListAdapter(
        onMovieItemClick = { movie -> onMovieItemClick(movie) },
        isWatchedMovie = { id -> isWatchedMovie(id) }
    )

    private val watchedMovieListAdapter =
        WatchedMovieListAdapter { id -> onWatchedOrInterestingItemClick(id) }
    private val favoriteMovieListAdapter =
        UserMovieListAdapter { movie -> onCollectionItemClick(movie) }
    private val delayedMovieListAdapter =
        UserMovieListAdapter { movie -> onCollectionItemClick(movie) }
    private val interestingMovieListAdapter =
        InterestingMovieListAdapter { id -> onWatchedOrInterestingItemClick(id) }
    private val userCollectionMovieListAdapter =
        UserMovieListAdapter { id -> onCollectionItemClick(id) }

    private val bundle = Bundle()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListPageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gridLayoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        binding.apply {
            imgBackButton.setOnClickListener {
                findNavController().popBackStack()
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.watchedCollectionMovieList.collect { list ->
                    if (list.isNotEmpty()) {
                        watchedMovieList = list
                    }
                }
            }

            recyclerViewMovies.layoutManager = gridLayoutManager
            viewLifecycleOwner.lifecycleScope.launch {
                textViewCollectionName.text = arguments?.getString(Arguments.COLLECTION_NAME)
                when (arguments?.getString(Arguments.TYPE)) {
                    Arguments.TOP_PREMIERES -> {
                        recyclerViewMovies.adapter = movieListAdapter
                        viewModel.premieres.collectLatest { movieList ->
                            movieListAdapter.submitList(movieList)
                        }
                    }

                    Arguments.TOP_POPULAR -> {
                        recyclerViewMovies.adapter = popularMoviePagedListAdapter
                        viewModel.pagedPopularMovies.collectLatest { movieList ->
                            popularMoviePagedListAdapter.submitData(movieList)
                        }
                    }

                    Arguments.ARG_FIRST_SELECTION -> {
                        recyclerViewMovies.adapter = firstSelectionMoviePagedListAdapter
                        viewModel.firstSelectionMovies.collectLatest { movieList ->
                            firstSelectionMoviePagedListAdapter.submitData(movieList)
                        }
                    }

                    Arguments.ARG_SECOND_SELECTION -> {
                        recyclerViewMovies.adapter = secondSelectionMoviePagedListAdapter
                        viewModel.secondSelectionMovies.collectLatest { movieList ->
                            secondSelectionMoviePagedListAdapter.submitData(movieList)
                        }
                    }

                    Arguments.TOP_250 -> {
                        recyclerViewMovies.adapter = top250MoviePagedListAdapter
                        viewModel.pagedTop250Movies.collectLatest { movieList ->
                            top250MoviePagedListAdapter.submitData(movieList)
                        }
                    }

                    Arguments.ARG_SERIES_TYPE -> {
                        recyclerViewMovies.adapter = seriesPagedListAdapter
                        viewModel.pagedTvSeries.collectLatest { movieList ->
                            seriesPagedListAdapter.submitData(movieList)
                        }
                    }

                    Arguments.ARG_SIMILAR_MOVIES -> {
                        recyclerViewMovies.adapter = similarMovieListAdapter
                        viewModel.similarMovies.collectLatest { similarMovieList ->
                            similarMovieListAdapter.submitList(similarMovieList)
                        }
                    }

                    Arguments.COLLECTION_TYPE_WATCHED -> {
                        recyclerViewMovies.adapter = watchedMovieListAdapter
                        viewModel.watchedCollectionMovieList.collectLatest { list ->
                            watchedMovieListAdapter.submitList(list)
                        }
                    }

                    Arguments.COLLECTION_TYPE_FAVORITE -> {
                        recyclerViewMovies.adapter = favoriteMovieListAdapter
                        viewModel.favoriteCollectionMovieList.collectLatest { collectionWithMovies ->
                            if (collectionWithMovies != null) {
                                val movieList = collectionWithMovies.movies
                                favoriteMovieListAdapter.submitList(movieList)
                            }
                        }
                    }

                    Arguments.COLLECTION_TYPE_DELAYED -> {
                        recyclerViewMovies.adapter = delayedMovieListAdapter
                        viewModel.delayedCollectionMovieList.collectLatest { collectionWithMovies ->
                            if (collectionWithMovies != null) {
                                val movieList = collectionWithMovies.movies
                                delayedMovieListAdapter.submitList(movieList)
                            }
                        }
                    }

                    Arguments.COLLECTION_TYPE_INTERESTING -> {
                        recyclerViewMovies.adapter = interestingMovieListAdapter
                        viewModel.interestingCollectionMovieList.collectLatest { interestingMovieList ->
                            interestingMovieListAdapter.submitList(interestingMovieList.reversed())
                        }
                    }

                    Arguments.COLLECTION_TYPE_USER_CUSTOM -> {
//                        val collectionId = arguments?.getString(Arguments.USER_COLLECTION_ID)
                        recyclerViewMovies.adapter = userCollectionMovieListAdapter
                        viewModel.userCollectionMovieList.collectLatest { collectionWithMovies ->
                            if (collectionWithMovies != null) {
                                val movieList = collectionWithMovies.movies
                                userCollectionMovieListAdapter.submitList(movieList)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onItemClick(movie: MovieDto) {
        val id = if (movie.filmId == null && movie.kinopoiskId != null) {
            movie.kinopoiskId
        } else movie.filmId!!
        viewModel.getMovieData(id)
        bundle.putInt(Arguments.ARG_KINOPOISK_ID, id)
        findNavController().navigate(R.id.action_listPageFragment_to_filmPageFragment, bundle)
    }

    private fun onMovieItemClick(movie: SimilarMoviesItemDto) {
        val id = movie.filmId
        viewModel.getMovieData(id)
        bundle.putInt(Arguments.ARG_KINOPOISK_ID, id)
        findNavController().navigate(R.id.action_listPageFragment_to_filmPageFragment, bundle)
    }

    private fun onWatchedOrInterestingItemClick(id: Int) {
        viewModel.getMovieData(id)
        bundle.putInt(Arguments.ARG_KINOPOISK_ID, id)
        findNavController().navigate(R.id.action_listPageFragment_to_filmPageFragment, bundle)
    }

    private fun isWatchedMovie(id: Int): Boolean {
        for (movie in watchedMovieList) {
            if (id == movie?.kinopoiskMovie?.kinopoiskId) return true
        }
        return false
    }

    private fun onCollectionItemClick(movie: KinopoiskMovie) {
        val id = movie.kinopoiskId
        viewModel.getMovieData(id)
        bundle.putInt(Arguments.ARG_KINOPOISK_ID, id)
        findNavController().navigate(R.id.action_listPageFragment_to_filmPageFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}