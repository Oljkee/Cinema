package com.example.lvl1final.presentation.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lvl1final.data.api.MovieDto
import com.example.lvl1final.databinding.FilmListItemBinding

class MovieListAdapter(
    private val onItemClick: (movie: MovieDto) -> Unit,
    private val isWatchedMovie: (id: Int) -> Boolean
) : ListAdapter<MovieDto, MovieViewHolder>(DiffUtilCallback()) {

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
            val genre: String = genres.joinToString(separator = ", ") { genreDto ->
                genreDto.genre
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

class DiffUtilCallback : DiffUtil.ItemCallback<MovieDto>() {
    override fun areItemsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean =
        oldItem.kinopoiskId == newItem.kinopoiskId

    override fun areContentsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean =
        oldItem == newItem

}

class MovieViewHolder(val binding: FilmListItemBinding) : RecyclerView.ViewHolder(binding.root)
