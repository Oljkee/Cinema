package com.example.lvl1final.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.example.lvl1final.databinding.GalleryListItemBinding
import com.example.lvl1final.domain.models.movieimpl.ImageImpl

class GalleryListAdapter(
    private val onGalleryItemClick: (image: ImageImpl) -> Unit
) : ListAdapter<ImageImpl, MovieGalleryViewHolder>(GalleryDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGalleryViewHolder {
        val binding =
            GalleryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieGalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieGalleryViewHolder, position: Int) {
        val context = holder.binding.root.context
        val item = getItem(position)
        holder.binding.root.setOnClickListener {
            onGalleryItemClick(item)
        }

        holder.binding.apply {
            Glide.with(context)
                .load(item?.imageUrl)
                .into(imgViewGalleryPhoto)

        }

        val layoutParams = holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
        if (position % 3 == 2) {
            layoutParams.isFullSpan = true
            holder.binding.apply {
                val cardViewGalleryItemLayoutParams = cardViewGalleryItem.layoutParams
                cardViewGalleryItemLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                val cardViewLayoutGalleryPhotoParams = cardViewGalleryPhoto.layoutParams
                cardViewLayoutGalleryPhotoParams.width = ViewGroup.LayoutParams.MATCH_PARENT

                val imgViewLayoutParams = imgViewGalleryPhoto.layoutParams
                val koef = imgViewLayoutParams.width.toFloat() / imgViewLayoutParams.height.toFloat()
                imgViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                imgViewLayoutParams.height = (koef * imgViewLayoutParams.height).toInt()
                imgViewGalleryPhoto.layoutParams = imgViewLayoutParams
            }
                    }
    }
}

class GalleryDiffUtilCallback : DiffUtil.ItemCallback<ImageImpl>() {
    override fun areItemsTheSame(oldItem: ImageImpl, newItem: ImageImpl): Boolean =
        oldItem.imageUrl == newItem.imageUrl


    override fun areContentsTheSame(oldItem: ImageImpl, newItem: ImageImpl): Boolean =
        oldItem == newItem

}

class MovieGalleryViewHolder(val binding: GalleryListItemBinding) :
    RecyclerView.ViewHolder(binding.root)
