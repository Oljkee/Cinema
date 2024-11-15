package com.example.lvl1final.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lvl1final.R
import com.example.lvl1final.databinding.FragmentProfileMainBinding
import com.example.lvl1final.domain.models.collection.MoviesCollection
import com.example.lvl1final.presentation.Arguments
import com.example.lvl1final.presentation.MainViewModel
import com.example.lvl1final.presentation.common.InterestingMovieListAdapter
import com.example.lvl1final.presentation.common.WatchedMovieListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileMainFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentProfileMainBinding? = null
    private val binding get() = _binding!!
    private val bundle = Bundle()
    private val watchedMovieListAdapter =
        WatchedMovieListAdapter { id -> onWatchedOrInterestingItemClick(id) }
    private val collectionCardListAdapter = CollectionCardListAdapter(
        onCollectionItemClick = { moviesCollection -> onCollectionItemClick(moviesCollection) },
        deleteCollectionButtonClick = { moviesCollection ->
            deleteCollectionButtonClick(
                moviesCollection
            )
        }
    )
    private val interestingMovieListAdapter =
        InterestingMovieListAdapter { id -> onWatchedOrInterestingItemClick(id) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            recyclerViewWatchedMovies.adapter = watchedMovieListAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                allWatchedFilms.setOnClickListener {
                    allMovies(Arguments.COLLECTION_TYPE_WATCHED, getString(R.string.watched))
                }
                viewModel.watchedCollectionMovieList.collectLatest { watchedMovieList ->
                    if (watchedMovieList.isEmpty()) {
                        recyclerViewWatchedMovies.visibility = View.GONE
                        textviewWatchedListIsEmpty.visibility = View.VISIBLE
                    } else {
                        watchedMovieListAdapter.submitList(watchedMovieList)
                        val displaySize =
                            if (watchedMovieList.size > 20) 20 else watchedMovieList.size
                        watchedMovieListAdapter.submitList(watchedMovieList.subList(0, displaySize))

                        recyclerViewWatchedMovies.visibility = View.VISIBLE
                        textviewWatchedListIsEmpty.visibility = View.GONE
                    }
                }
            }

            linearLayoutCreateCollection.setOnClickListener {
                findNavController().navigate(
                    R.id.action_profileMainFragment_to_createCollectionDialogFragment,
                    bundle
                )
            }

            recyclerViewCollections.adapter = collectionCardListAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.collectionList.collectLatest { collectionList ->
                    collectionCardListAdapter.submitList(collectionList)
                }
            }

            recyclerViewInterestingMovies.adapter = interestingMovieListAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                allInterestingMovies.setOnClickListener {
                    allMovies(
                        Arguments.COLLECTION_TYPE_INTERESTING,
                        getString(R.string.interesting)
                    )
                }
                viewModel.interestingCollectionMovieList.collectLatest { movieList ->
                    if (movieList.isEmpty()) {
                        recyclerViewInterestingMovies.visibility = View.GONE
                        textviewInterestingListIsEmpty.visibility = View.VISIBLE
                    } else {
                        interestingMovieListAdapter.submitList(movieList)
                        recyclerViewInterestingMovies.visibility = View.VISIBLE
                        textviewInterestingListIsEmpty.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun allMovies(type: String, collectionName: String) {
        bundle.putString(Arguments.TYPE, type)
        bundle.putString(Arguments.COLLECTION_NAME, collectionName)
        findNavController().navigate(R.id.action_profileMainFragment_to_listPageFragment, bundle)
    }

    private fun onCollectionItemClick(moviesCollection: MoviesCollection) {
        viewModel.getTheCollectionMovies(moviesCollection.id!!)

        when (moviesCollection.id) {
            1 -> bundle.putString(Arguments.TYPE, Arguments.COLLECTION_TYPE_FAVORITE)
            2 -> bundle.putString(Arguments.TYPE, Arguments.COLLECTION_TYPE_DELAYED)
            else -> bundle.putString(Arguments.TYPE, Arguments.COLLECTION_TYPE_USER_CUSTOM)
        }

        bundle.putString(Arguments.COLLECTION_NAME, moviesCollection.collectionName)
        findNavController().navigate(R.id.action_profileMainFragment_to_listPageFragment, bundle)
    }

    private fun deleteCollectionButtonClick(moviesCollection: MoviesCollection) {
        viewModel.deleteCollection(moviesCollection)
    }

    private fun onWatchedOrInterestingItemClick(id: Int) {
        viewModel.getMovieData(id)
        bundle.putInt(Arguments.ARG_KINOPOISK_ID, id)
        findNavController().navigate(
            R.id.action_profileMainFragment_to_movie_info_nested_graph,
            bundle
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}