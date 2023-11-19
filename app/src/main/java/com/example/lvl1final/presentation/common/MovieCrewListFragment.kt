package com.example.lvl1final.presentation.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lvl1final.R
import com.example.lvl1final.data.api.MovieStaffDto
import com.example.lvl1final.databinding.FragmentMovieCrewListBinding
import com.example.lvl1final.presentation.Arguments
import com.example.lvl1final.presentation.MainViewModel
import kotlinx.coroutines.launch


class MovieCrewListFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentMovieCrewListBinding? = null
    private val binding get() = _binding!!
    private val movieCrewListAdapter = MovieStaffListAdapter { staff -> onActorItemClick(staff) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieCrewListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            textViewListName.text = arguments?.getString(Arguments.MOVIE_CREW_TYPE_NAME)
            recyclerViewMovieCrew.adapter = movieCrewListAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.movieCrewList.collect { movieCrewList ->
                    movieCrewListAdapter.submitList(movieCrewList)
                }
            }

            imgBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun onActorItemClick(staff: MovieStaffDto) {
        val id = staff.staffId
        viewModel.getActorInfo(id)
        findNavController().navigate(R.id.action_movieCrewListFragment_to_actorPageFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}