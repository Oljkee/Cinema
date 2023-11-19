package com.example.lvl1final.presentation.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lvl1final.databinding.FilmListItemBinding
import com.example.lvl1final.data.entity.KinopoiskMovie

class UserMovieListAdapter (
    private val onItemClick: (movie: KinopoiskMovie) -> Unit
) : ListAdapter<KinopoiskMovie, FilmListItemViewHolder>(KinopoiskMovieDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmListItemViewHolder {
        val binding =
            FilmListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmListItemViewHolder, position: Int) {
        val context = holder.binding.root.context
        val item = getItem(position)

        item.apply {
            holder.binding.root.setOnClickListener {
                onItemClick(this)
            }

            val photoUrl = posterUrl
            val name = movieName
            val genre = movieGenre

            holder.binding.apply {
                Glide.with(context)
                    .load(photoUrl)
                    .into(imgViewPoster)

                textviewName.text = name
                textviewGenre.text = genre
                if(ratingKinopoisk != null) {
                    textviewRating.visibility = View.VISIBLE
                    textviewRating.text = ratingKinopoisk.toString()
                } else {
                    textviewRating.visibility = View.GONE
                }
            }
        }
    }
}

class KinopoiskMovieDiffUtilCallback : DiffUtil.ItemCallback<KinopoiskMovie>() {
    override fun areItemsTheSame(oldItem: KinopoiskMovie, newItem: KinopoiskMovie): Boolean = oldItem.kinopoiskId == newItem.kinopoiskId

    override fun areContentsTheSame(oldItem: KinopoiskMovie, newItem: KinopoiskMovie): Boolean = oldItem == newItem

}

class FilmListItemViewHolder(val binding: FilmListItemBinding) : RecyclerView.ViewHolder(binding.root)
