package com.example.lvl1final.presentation.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.lvl1final.R
import com.example.lvl1final.data.api.ImageDto
import com.example.lvl1final.databinding.FragmentGalleryBinding
import com.example.lvl1final.presentation.Arguments
import com.example.lvl1final.presentation.MainViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GalleryFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private val galleryListAdapter = GalleryListAdapter { image -> onGalleryItemClick(image) }
    private val bundle = Bundle()
    private var firstNotEmptyImageList: StateFlow<List<ImageDto>>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            imgBackButton.setOnClickListener { findNavController().popBackStack() }

            recyclerViewImages.layoutManager =
                StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

            setNewChipWithClickListener(
                viewModel.movieStillImages,
                getString(R.string.still),
                chipGroupImages,
                recyclerViewImages
            )
            setNewChipWithClickListener(
                viewModel.movieShootingImages,
                getString(R.string.shooting),
                chipGroupImages,
                recyclerViewImages
            )
            setNewChipWithClickListener(
                viewModel.moviePosterImages,
                getString(R.string.poster),
                chipGroupImages,
                recyclerViewImages
            )
            setNewChipWithClickListener(
                viewModel.movieFanArtImages,
                getString(R.string.fan_art),
                chipGroupImages,
                recyclerViewImages
            )
            setNewChipWithClickListener(
                viewModel.moviePromoImages,
                getString(R.string.promo),
                chipGroupImages,
                recyclerViewImages
            )
            setNewChipWithClickListener(
                viewModel.movieConceptImages,
                getString(R.string.concept),
                chipGroupImages,
                recyclerViewImages
            )
            setNewChipWithClickListener(
                viewModel.movieWallpaperImages,
                getString(R.string.wallpaper),
                chipGroupImages,
                recyclerViewImages
            )
            setNewChipWithClickListener(
                viewModel.movieCoverImages,
                getString(R.string.cover),
                chipGroupImages,
                recyclerViewImages
            )
            setNewChipWithClickListener(
                viewModel.movieScreenshotImages,
                getString(R.string.screenshot),
                chipGroupImages,
                recyclerViewImages
            )

            viewLifecycleOwner.lifecycleScope.launch {
                firstNotEmptyImageList?.collectLatest { list ->
                    recyclerViewImages.adapter = galleryListAdapter
                    galleryListAdapter.submitList(list)
                }
            }
        }
    }

    private fun onGalleryItemClick(image: ImageDto) {
        bundle.putString(Arguments.ARG_GALLERY_IMAGE_URL, image.imageUrl)
        findNavController().navigate(R.id.action_galleryFragment_to_imageDialogFragment, bundle)
    }


    private fun setNewChipWithClickListener(
        stateFlow: StateFlow<List<ImageDto>>,
        chipText: String,
        chipGroup: ChipGroup,
        recyclerViewImages: RecyclerView
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            stateFlow.collectLatest { list ->
                if (list.isNotEmpty()) {
                    if (firstNotEmptyImageList == null) {
                        firstNotEmptyImageList = stateFlow
                    }
                    val chip = Chip(context)
                    chip.text = chipText
                    chip.setOnClickListener {
                        recyclerViewImages.adapter = galleryListAdapter
                        galleryListAdapter.submitList(list)
                    }
                    chipGroup.addView(chip)
                }
            }
        }
    }
}