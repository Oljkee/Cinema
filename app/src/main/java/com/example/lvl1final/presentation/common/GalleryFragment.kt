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
import com.example.lvl1final.databinding.FragmentGalleryBinding
import com.example.lvl1final.domain.models.movieimpl.ImageImpl
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
    private var firstNotEmptyImageList: StateFlow<List<ImageImpl>>? = null


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

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.allMovieImages.collectLatest { imageMap ->
                    imageMap.forEach { (imageType, imageList) ->
                        setNewChipWithClickListener(
                            imageList,
                            imageType.toLocalizedString(),
                            chipGroupImages,
                            recyclerViewImages
                        )
                    }
                    if (chipGroupImages.childCount > 0) {
                        val firstChip = chipGroupImages.getChildAt(0) as Chip
                        firstChip.performClick()
                    }
                }
            }
        }
    }

    private fun onGalleryItemClick(image: ImageImpl) {
        bundle.putString(Arguments.ARG_GALLERY_IMAGE_URL, image.imageUrl)
        findNavController().navigate(R.id.action_galleryFragment_to_imageDialogFragment, bundle)
    }


    private fun setNewChipWithClickListener(
        list: List<ImageImpl>,
        chipText: String,
        chipGroup: ChipGroup,
        recyclerViewImages: RecyclerView
    ) {
        if (list.isNotEmpty()) {
            val chip = Chip(context)
            chip.text = chipText
            chip.setOnClickListener {
                recyclerViewImages.adapter = galleryListAdapter
                galleryListAdapter.submitList(list)
            }
            chipGroup.addView(chip)
        }
    }

    fun String.toLocalizedString(): String {
        return when (this) {
            Arguments.MOVIE_IMAGES_TYPE_STILL -> getString(R.string.still)
            Arguments.MOVIE_IMAGES_TYPE_SHOOTING -> getString(R.string.shooting)
            Arguments.MOVIE_IMAGES_TYPE_POSTER -> getString(R.string.poster)
            Arguments.MOVIE_IMAGES_TYPE_FAN_ART -> getString(R.string.fan_art)
            Arguments.MOVIE_IMAGES_TYPE_PROMO -> getString(R.string.promo)
            Arguments.MOVIE_IMAGES_TYPE_CONCEPT -> getString(R.string.concept)
            Arguments.MOVIE_IMAGES_TYPE_WALLPAPER -> getString(R.string.wallpaper)
            Arguments.MOVIE_IMAGES_TYPE_COVER -> getString(R.string.cover)
            Arguments.MOVIE_IMAGES_TYPE_SCREENSHOT -> getString(R.string.screenshot)
            else -> this
        }
    }
}