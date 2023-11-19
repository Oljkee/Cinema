package com.example.lvl1final.presentation.common

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lvl1final.R
import com.example.lvl1final.data.api.FilmDto
import com.example.lvl1final.databinding.FragmentFilmographyBinding
import com.example.lvl1final.presentation.Arguments
import com.example.lvl1final.presentation.MainViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.launch

class FilmographyFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentFilmographyBinding? = null
    private val binding get() = _binding!!
    private val movieListAdapter = FilmographyMovieListAdapter { film -> onItemClick(film) }
    private var firstNotEmptyFilmList: List<FilmDto>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmographyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            imgBackButton.setOnClickListener { findNavController().popBackStack() }

            val type = arguments?.getString(Arguments.ARG_MOVIE_LIST)
            viewLifecycleOwner.lifecycleScope.launch {
                recyclerViewMovies.adapter = movieListAdapter
                viewModel.actorInfo.collect { actorInfo ->
                    actorInfo?.apply {
                        textViewPersonName.text = nameRu ?: nameEn
                        Log.d("Actor", "PersonID: ${actorInfo.personId}")
                        when (type) {
                            Arguments.ARG_ACTOR_FILMOGRAPHY -> {
                                textViewCollectionName.text = getString(R.string.filmography)
                                val distinctList = films.distinctBy { film -> film.filmId }
                                Log.d("Actor", "all: ${distinctList.size}")

                                val groupedMap: Map<String, List<FilmDto>> =
                                    distinctList.groupBy { it.professionKey }
                                for (key in groupedMap.keys) {
                                    val movieListForProfession = groupedMap[key]
                                    if (!movieListForProfession.isNullOrEmpty()) {
                                        if (firstNotEmptyFilmList == null) {
                                            firstNotEmptyFilmList = movieListForProfession
                                        }
                                        val chipGroup: ChipGroup = chipGroupProfessions
                                        val chip = Chip(context)
                                        chip.text = when (key) {
                                            Arguments.MOVIE_STAFF_PROFESSION_WRITER -> (getString(R.string.writer))
                                            Arguments.MOVIE_STAFF_PROFESSION_OPERATOR -> {
                                                getString(R.string.operator)
                                            }

                                            Arguments.MOVIE_STAFF_PROFESSION_EDITOR -> {
                                                getString(R.string.editor)
                                            }

                                            Arguments.MOVIE_STAFF_PROFESSION_COMPOSER -> {
                                                getString(R.string.composer)
                                            }

                                            Arguments.MOVIE_STAFF_PROFESSION_PRODUCER_USSR -> {
                                                getString(R.string.producer_ussr)
                                            }

                                            Arguments.MOVIE_STAFF_PROFESSION_TRANSLATOR -> {
                                                getString(R.string.translator)
                                            }

                                            Arguments.MOVIE_STAFF_PROFESSION_DIRECTOR -> {
                                                getString(R.string.director)
                                            }

                                            Arguments.MOVIE_STAFF_PROFESSION_DESIGN -> {
                                                getString(R.string.design)
                                            }

                                            Arguments.MOVIE_STAFF_PROFESSION_PRODUCER -> {
                                                getString(R.string.producer)
                                            }

                                            Arguments.MOVIE_STAFF_PROFESSION_ACTOR -> {
                                                if (actorInfo.sex == Arguments.ARG_FEMALE) getString(
                                                    R.string.actress
                                                )
                                                else getString(R.string.actor)
                                            }

                                            Arguments.MOVIE_STAFF_PROFESSION_VOICE_DIRECTOR -> {
                                                getString(R.string.voice_director)
                                            }

                                            Arguments.MOVIE_STAFF_PROFESSION_UNKNOWN -> {
                                                getString(R.string.unknown)
                                            }

                                            Arguments.MOVIE_STAFF_PROFESSION_HIMSELF -> {
                                                getString(R.string.himself)
                                            }

                                            Arguments.MOVIE_STAFF_PROFESSION_HERSELF -> {
                                                getString(R.string.herself)
                                            }

                                            Arguments.MOVIE_STAFF_PROFESSION_HRONO_TITR_MALE -> {
                                                getString(R.string.hrono_titr_male)
                                            }

                                            Arguments.MOVIE_STAFF_PROFESSION_HRONO_TITR_FEMALE -> {
                                                getString(R.string.hrono_titr_female)
                                            }

                                            Arguments.MOVIE_STAFF_PROFESSION_VOICE_FEMALE -> {
                                                getString(R.string.voice_female)
                                            }

                                            Arguments.MOVIE_STAFF_PROFESSION_VOICE_MALE -> {
                                                getString(R.string.voice_male)
                                            }

                                            else -> {
                                                "??"
                                            }
                                        }
                                        chip.setOnClickListener {
                                            recyclerViewMovies.adapter = movieListAdapter
                                            movieListAdapter.submitList(movieListForProfession)
                                        }
                                        chipGroup.addView(chip)
                                    }
                                }

                                viewLifecycleOwner.lifecycleScope.launch {
                                    if (firstNotEmptyFilmList != null) {
                                        recyclerViewMovies.adapter = movieListAdapter
                                        movieListAdapter.submitList(firstNotEmptyFilmList)
                                    }
                                }
                            }

                            else -> {
                                textViewCollectionName.text = getString(R.string.the_best)
                                val distinctList = films.distinctBy { film -> film.filmId }
                                val sortedFilms =
                                    distinctList.sortedByDescending { film -> film.rating }
                                val displaySize =
                                    if (sortedFilms.size > 10) 10 else sortedFilms.size
                                Log.d("Actor", "best: ${sortedFilms.size}")
                                movieListAdapter.submitList(sortedFilms.subList(0, displaySize))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onItemClick(film: FilmDto) {
        val id = film.filmId
        viewModel.getMovieData(id)
        findNavController().navigate(R.id.action_filmographyFragment_to_filmPageFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}