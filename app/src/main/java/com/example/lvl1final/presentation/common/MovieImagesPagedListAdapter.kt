package com.example.lvl1final.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lvl1final.data.api.ImageDto
import com.example.lvl1final.databinding.GalleryListItemBinding

class MovieImagesPagedListAdapter(
    private val onGalleryItemClick: (image: ImageDto) -> Unit
) : ListAdapter<ImageDto, PagedMovieImagesViewHolder>(ImagesPagedDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagedMovieImagesViewHolder {
        val binding =
            GalleryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagedMovieImagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagedMovieImagesViewHolder, position: Int) {
        val context = holder.binding.root.context
        val item = getItem(position)
        holder.binding.root.setOnClickListener {
            onGalleryItemClick(item)
        }

        item?.apply {
            val photoUrl = previewUrl

            holder.binding.apply {
                Glide.with(context)
                    .load(photoUrl)
                    .into(imgViewGalleryPhoto)
            }
        }
    }
}

class ImagesPagedDiffUtilCallback : DiffUtil.ItemCallback<ImageDto>() {
    override fun areItemsTheSame(oldItem: ImageDto, newItem: ImageDto): Boolean =
        oldItem.imageUrl == newItem.imageUrl

    override fun areContentsTheSame(oldItem: ImageDto, newItem: ImageDto): Boolean =
        oldItem == newItem
}

class PagedMovieImagesViewHolder(val binding: GalleryListItemBinding) :
    RecyclerView.ViewHolder(binding.root)
