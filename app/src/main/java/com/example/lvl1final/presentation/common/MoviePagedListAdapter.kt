package com.example.lvl1final.presentation.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lvl1final.databinding.FilmListItemBinding
import com.example.lvl1final.domain.models.movieimpl.MovieImpl

class MoviePagedListAdapter(
    private val onItemClick: (movie: MovieImpl) -> Unit,
    private val isWatchedMovie: (id: Int) -> Boolean
) : PagingDataAdapter<MovieImpl, PagedMovieViewHolder>(PagedDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagedMovieViewHolder {
        val binding =
            FilmListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagedMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagedMovieViewHolder, position: Int) {
        val context = holder.binding.root.context

        val item = getItem(position)

        item?.apply {
            holder.binding.root.setOnClickListener {
                onItemClick(this)
            }

            val photoUrl = posterUrlPreview

            val name = nameRu ?: (nameOriginal ?: nameEn)

            val genre: String = genres.joinToString(separator = ", ") { genre ->
                genre.genre
            }
            val id = kinopoiskId ?: filmId
            holder.binding.apply {
                watchedMovieGradientBackground.visibility = if (isWatchedMovie(id!!))
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

class PagedDiffUtilCallback : DiffUtil.ItemCallback<MovieImpl>() {
    override fun areItemsTheSame(oldItem: MovieImpl, newItem: MovieImpl): Boolean =
        oldItem.filmId == newItem.filmId

    override fun areContentsTheSame(oldItem: MovieImpl, newItem: MovieImpl): Boolean =
        oldItem == newItem
}

class PagedMovieViewHolder(val binding: FilmListItemBinding) : RecyclerView.ViewHolder(binding.root)
