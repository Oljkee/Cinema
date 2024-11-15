package com.example.lvl1final.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lvl1final.databinding.GalleryListItemBinding
import com.example.lvl1final.domain.models.movieimpl.ImageImpl

class MovieImagesPagedListAdapter(
    private val onGalleryItemClick: (image: ImageImpl) -> Unit
) : ListAdapter<ImageImpl, PagedMovieImagesViewHolder>(ImagesPagedDiffUtilCallback()) {

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

class ImagesPagedDiffUtilCallback : DiffUtil.ItemCallback<ImageImpl>() {
    override fun areItemsTheSame(oldItem: ImageImpl, newItem: ImageImpl): Boolean =
        oldItem.imageUrl == newItem.imageUrl

    override fun areContentsTheSame(oldItem: ImageImpl, newItem: ImageImpl): Boolean =
        oldItem == newItem
}

class PagedMovieImagesViewHolder(val binding: GalleryListItemBinding) :
    RecyclerView.ViewHolder(binding.root)
