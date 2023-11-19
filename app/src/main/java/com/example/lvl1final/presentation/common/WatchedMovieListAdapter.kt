package com.example.lvl1final.presentation.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lvl1final.databinding.FilmListItemBinding
import com.example.lvl1final.data.entity.WatchedMovieWithKinopoiskMovie

class WatchedMovieListAdapter(
    private val onItemClick: (id: Int) -> Unit
) : ListAdapter<WatchedMovieWithKinopoiskMovie, WatchedMovieViewHolder>(WatchedMovieDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchedMovieViewHolder {
        val binding =
            FilmListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WatchedMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WatchedMovieViewHolder, position: Int) {
        val context = holder.binding.root.context
        val item = getItem(position)

        item.apply {
            holder.binding.root.setOnClickListener {
                onItemClick(kinopoiskMovie.kinopoiskId)
            }

            val photoUrl = kinopoiskMovie.posterUrl
            val name = kinopoiskMovie.movieName
            val genre = kinopoiskMovie.movieGenre
            val rating = kinopoiskMovie.ratingKinopoisk

            holder.binding.apply {
                Glide.with(context)
                    .load(photoUrl)
                    .into(imgViewPoster)

                textviewName.text = name
                textviewGenre.text = genre
                if (rating != null) {
                    textviewRating.visibility = View.VISIBLE
                    textviewRating.text = rating.toString()
                } else {
                    textviewRating.visibility = View.GONE
                }
            }
        }
    }
}

class WatchedMovieDiffUtilCallback : DiffUtil.ItemCallback<WatchedMovieWithKinopoiskMovie>() {
    override fun areItemsTheSame(
        oldItem: WatchedMovieWithKinopoiskMovie,
        newItem: WatchedMovieWithKinopoiskMovie
    ): Boolean = oldItem.watchedMovie.id == newItem.watchedMovie.id

    override fun areContentsTheSame(
        oldItem: WatchedMovieWithKinopoiskMovie,
        newItem: WatchedMovieWithKinopoiskMovie
    ): Boolean = oldItem == newItem

}

class WatchedMovieViewHolder(val binding: FilmListItemBinding) :
    RecyclerView.ViewHolder(binding.root)
