package com.example.lvl1final.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lvl1final.R
import com.example.lvl1final.databinding.FragmentSearchMainBinding
import com.example.lvl1final.domain.models.movieimpl.MovieImpl
import com.example.lvl1final.presentation.Arguments
import com.example.lvl1final.presentation.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchMainFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentSearchMainBinding? = null
    private val binding get() = _binding!!
    private val bundle = Bundle()
    private val searchMoviePagingDataAdapter =
        SearchMoviePagingDataAdapter { movie -> onMovieListItemClick(movie) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchMainBinding.inflate(layoutInflater, container, false)
        binding.textInputLayoutSearchLine.isEndIconCheckable = true
        binding.edSearchLine.hint = getString(R.string.search_text_hint)
        binding.recyclerViewSearchMovies.adapter = searchMoviePagingDataAdapter
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.isEmptyList.collect {isEmptyList ->
                    if (isEmptyList) {
                        textViewNothingFound.visibility = View.VISIBLE
                        recyclerViewSearchMovies.visibility = View.GONE
                    } else {
                        textViewNothingFound.visibility = View.GONE
                        recyclerViewSearchMovies.visibility = View.VISIBLE
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.movieSearchFlow.collect {
                    searchMoviePagingDataAdapter.submitData(it)
                }
            }



            edSearchLine.addTextChangedListener(object : TextWatcher {
                private var searchJob: Job? = null

                override fun beforeTextChanged(
                    p0: CharSequence?,
                    p1: Int,
                    p2: Int,
                    p3: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    val searchText = s?.trim() ?: ""

                    searchJob?.cancel()

                    searchJob = viewLifecycleOwner.lifecycleScope.launch {
                        delay(1000)
                        viewModel.updateSearchKeyword(searchText.toString())
                    }
                }

                override fun afterTextChanged(p0: Editable?) {}
            })

            textInputLayoutSearchLine.setEndIconOnClickListener {
                findNavController().navigate(R.id.action_searchMainFragment_to_searchSettingsFragment)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.updateSearchKeyword("")
    }

    private fun onMovieListItemClick(movie: MovieImpl) {
        val id = movie.kinopoiskId!!
        viewModel.getMovieData(id)
        bundle.putInt(Arguments.ARG_KINOPOISK_ID, id)
        findNavController().navigate(
            R.id.action_searchMainFragment_to_movie_info_nested_graph,
            bundle
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}