package com.example.lvl1final.presentation.common

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.bumptech.glide.Glide
import com.example.lvl1final.R
import com.example.lvl1final.data.entity.Collection
import com.example.lvl1final.data.api.KinopoiskMovieInfoDto
import com.example.lvl1final.databinding.FragmentExtraMenuBottomSheetBinding
import com.example.lvl1final.data.entity.CollectionMovie
import com.example.lvl1final.data.entity.KinopoiskMovie
import com.example.lvl1final.presentation.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ExtraMenuBottomSheetFragment : BottomSheetDialogFragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentExtraMenuBottomSheetBinding? = null
    private val binding get() = _binding!!

    private var movieId = 0
    private var movie: KinopoiskMovieInfoDto? = null
    private val collectionListAdapter =
        CollectionListAdapter { moviesCollection, isChecked ->
            onCollectionItemClick(moviesCollection, isChecked)
        }

    private val addNewCollectionListAdapter = AddNewCollectionListAdapter { onClick() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExtraMenuBottomSheetBinding.inflate(layoutInflater, container, false)
        viewModel.clearCreatedCollectionId()
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            addToCollection.imageViewIcon.setImageResource(R.drawable.ic_folder_plus)
            addToCollection.textviewCreateCollection.text = getString(R.string.add_to_collection)

            val concatAdapter = ConcatAdapter(collectionListAdapter, addNewCollectionListAdapter)
            recyclerViewCollectionList.adapter = concatAdapter

            this.imageViewCancel.setOnClickListener {
                dismiss()
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.movieInfo.collect { movieInfo ->
                    movieInfo?.apply {
                        movie = this
                        movieId = kinopoiskId
                        Glide.with(root.context)
                            .load(posterUrlPreview)
                            .into(movieCard.imgViewMoviePoster)

                        movieCard.textviewMovieName.text = nameRu ?: (nameOriginal ?: nameEn)

                        val genre: String = genres.joinToString(separator = ", ") { genreDto ->
                            genreDto.genre
                        }
                        movieCard.textviewYearGenre.text = genre
                        ratingKinopoisk
                        if (ratingKinopoisk != null) {
                            movieCard.textviewRating.visibility = View.VISIBLE
                            movieCard.textviewRating.text = ratingKinopoisk.toString()
                        } else {
                            movieCard.textviewRating.visibility = View.GONE
                        }

                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.collectionList.combine(viewModel.collectionIdListWithMovie) { collections, ids ->
                    val result = mutableListOf<Collection>()
                    for (collection in collections) {
                        val isMovieInCollection = ids?.any { it == collection.id } ?: false
                        result.add(
                            Collection(
                                id = collection.id!!,
                                collectionName = collection.collectionName,
                                numberOfMovies = collection.numberOfMovies,
                                isMovieInCollection = isMovieInCollection
                            )
                        )
                    }
                    result
                }.collect { movieCollectionListResult ->
                    collectionListAdapter.submitList(movieCollectionListResult)
                    concatAdapter.notifyDataSetChanged()
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.createdCollectionId.collect {
                    if (it != null) {
                        movie?.apply {
                            val collectionMovie = CollectionMovie(it, kinopoiskId)
                            val name = nameRu ?: (nameOriginal ?: nameEn)
                            val genre: String = genres.joinToString(separator = ", ") { genreDto ->
                                genreDto.genre
                            }
                            val kinopoiskMovie = KinopoiskMovie(
                                kinopoiskId,
                                posterUrlPreview,
                                name,
                                genre,
                                year,
                                ratingKinopoisk
                            )
                            viewModel.insertMovieToCollection(kinopoiskMovie, collectionMovie)
                        }
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.collectionsUpdateState.collect {
                    viewModel.getCollectionIdListWithMovie(movieId)
                }
            }
        }
    }

    private fun onCollectionItemClick(moviesCollection: Collection, isChecked: Boolean) {
        movie?.apply {
            val collectionMovie = CollectionMovie(moviesCollection.id, kinopoiskId)
            if (isChecked) {
                viewModel.deleteMovieFromCollection(collectionMovie)
            } else {
                val name = nameRu ?: (nameOriginal ?: nameEn)
                val genre: String = genres.joinToString(separator = ", ") { genreDto ->
                    genreDto.genre
                }
                val kinopoiskMovie = KinopoiskMovie(
                    kinopoiskId,
                    posterUrlPreview,
                    name,
                    genre,
                    year,
                    ratingKinopoisk
                )
                viewModel.insertMovieToCollection(kinopoiskMovie, collectionMovie)
            }
        }
    }

    private fun onClick() {
        findNavController().navigate(R.id.action_extraMenuBottomSheetFragment_to_createCollectionDialogFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}