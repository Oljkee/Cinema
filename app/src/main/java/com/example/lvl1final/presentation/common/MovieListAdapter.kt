package com.example.lvl1final.presentation.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lvl1final.databinding.FilmListItemBinding
import com.example.lvl1final.domain.models.movieimpl.MovieImpl

class MovieListAdapter(
    private val onItemClick: (movie: MovieImpl) -> Unit,
    private val isWatchedMovie: (id: Int) -> Boolean
) : ListAdapter<MovieImpl, MovieViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            FilmListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val context = holder.binding.root.context
        val item = getItem(position)

        item.apply {
            holder.binding.root.setOnClickListener {
                onItemClick(this)
            }

            val photoUrl = posterUrlPreview
            val name = nameRu ?: nameOriginal ?: nameEn
            val genre: String = genres.joinToString(separator = ", ") { genre ->
                genre.genre
            }

            holder.binding.apply {
                watchedMovieGradientBackground.visibility = if (isWatchedMovie(kinopoiskId!!))
                    View.VISIBLE
                else View.GONE

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

class DiffUtilCallback : DiffUtil.ItemCallback<MovieImpl>() {
    override fun areItemsTheSame(oldItem: MovieImpl, newItem: MovieImpl): Boolean =
        oldItem.kinopoiskId == newItem.kinopoiskId

    override fun areContentsTheSame(oldItem: MovieImpl, newItem: MovieImpl): Boolean =
        oldItem == newItem

}

class MovieViewHolder(val binding: FilmListItemBinding) : RecyclerView.ViewHolder(binding.root)
