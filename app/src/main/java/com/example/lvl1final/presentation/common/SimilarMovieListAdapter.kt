package com.example.lvl1final.presentation.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lvl1final.data.api.SimilarMoviesItemDto
import com.example.lvl1final.databinding.FilmListItemBinding

class SimilarMovieListAdapter(
    private val onMovieItemClick: (movie: SimilarMoviesItemDto) -> Unit,
    private val isWatchedMovie: (id: Int) -> Boolean
) : ListAdapter<SimilarMoviesItemDto, SimilarMovieViewHolder>(SimilarDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMovieViewHolder {
        val binding =
            FilmListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimilarMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimilarMovieViewHolder, position: Int) {
        val context = holder.binding.root.context
        val item = getItem(position)

        item.apply {
            holder.binding.root.setOnClickListener {
                onMovieItemClick(this)
            }

            val photoUrl = posterUrlPreview
            val name = nameRu ?: (nameOriginal ?: nameEn)

            holder.binding.apply {
                watchedMovieGradientBackground.visibility = if (isWatchedMovie(filmId))
                    View.VISIBLE
                else View.GONE

                Glide.with(context)
                    .load(photoUrl)
                    .into(imgViewPoster)

                textviewName.text = name
                textviewRating.visibility = View.GONE

            }
        }
    }
}

class SimilarDiffUtilCallback : DiffUtil.ItemCallback<SimilarMoviesItemDto>() {
    override fun areItemsTheSame(
        oldItem: SimilarMoviesItemDto,
        newItem: SimilarMoviesItemDto
    ): Boolean = oldItem.filmId == newItem.filmId


    override fun areContentsTheSame(
        oldItem: SimilarMoviesItemDto,
        newItem: SimilarMoviesItemDto
    ): Boolean = oldItem == newItem

}

class SimilarMovieViewHolder(val binding: FilmListItemBinding) :
    RecyclerView.ViewHolder(binding.root)
