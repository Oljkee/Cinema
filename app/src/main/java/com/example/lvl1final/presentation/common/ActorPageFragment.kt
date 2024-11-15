package com.example.lvl1final.presentation.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.lvl1final.R
import com.example.lvl1final.databinding.FragmentActorPageBinding
import com.example.lvl1final.domain.models.collection.WatchedMovieWithKinopoiskMovie
import com.example.lvl1final.domain.models.movieimpl.FilmImpl
import com.example.lvl1final.presentation.Arguments
import com.example.lvl1final.presentation.MainViewModel
import kotlinx.coroutines.launch

class ActorPageFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentActorPageBinding? = null
    private val binding get() = _binding!!
    private val theBestMovieListAdapter = ActorMovieListAdapter(
        onItemClick = { film -> onItemClick(film) },
        isWatchedMovie = { id -> isWatchedMovie(id) }
    )
    private var watchedMovieList = emptyList<WatchedMovieWithKinopoiskMovie?>()
    private val bundle = Bundle()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActorPageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            imgBackButton.setOnClickListener { findNavController().popBackStack() }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.watchedCollectionMovieList.collect { list ->
                    if (list.isNotEmpty()) {
                        watchedMovieList = list
                    }
                }
            }

            recyclerViewTheBestMovies.adapter = theBestMovieListAdapter

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.actorInfo.collect { actorInfo ->
                    actorInfo?.apply {
                        Glide.with(root.context)
                            .load(posterUrl)
                            .into(imgViewAvatar)

                        textviewName.text = nameRu ?: nameEn
                        textviewDescription.text = profession

                        val distinctList = films.distinctBy { film -> film.filmId }
                        textviewMoviesCount.text = distinctList.size.toString()

                        val sortedFilms = distinctList.sortedByDescending { film -> film.rating }
                        val displaySize = if (sortedFilms.size > 10) 10 else sortedFilms.size

                        theBestMovieListAdapter.submitList(sortedFilms.subList(0, displaySize))
                    }
                }
            }

            allBestMovies.setOnClickListener {
                setMovieListToFilmographyFragment(Arguments.ARG_ACTOR_BEST_MOVIES)
            }

            allFilmography.setOnClickListener {
                setMovieListToFilmographyFragment(Arguments.ARG_ACTOR_FILMOGRAPHY)
            }
        }
    }

    private fun setMovieListToFilmographyFragment(movieListType: String) {
        bundle.putString(Arguments.ARG_MOVIE_LIST, movieListType)
        findNavController().navigate(R.id.action_actorPageFragment_to_filmographyFragment, bundle)
    }

    private fun isWatchedMovie(id: Int): Boolean {
        if (watchedMovieList.isNotEmpty()) {
            for (movie in watchedMovieList) {
                if (id == movie?.kinopoiskMovie?.kinopoiskId) return true
            }
        }
        return false
    }

    private fun onItemClick(film: FilmImpl) {
        val id = film.filmId
        viewModel.getMovieData(id)
        bundle.putInt(Arguments.ARG_KINOPOISK_ID, id)
        findNavController().navigate(R.id.action_actorPageFragment_to_filmPageFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}