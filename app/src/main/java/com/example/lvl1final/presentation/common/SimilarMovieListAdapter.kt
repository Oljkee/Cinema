package com.example.lvl1final.presentation.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lvl1final.databinding.FilmListItemBinding
import com.example.lvl1final.domain.models.movieimpl.SimilarMoviesItemImpl

class SimilarMovieListAdapter(
    private val onMovieItemClick: (movie: SimilarMoviesItemImpl) -> Unit,
    private val isWatchedMovie: (id: Int) -> Boolean
) : ListAdapter<SimilarMoviesItemImpl, SimilarMovieViewHolder>(SimilarDiffUtilCallback()) {

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

class SimilarDiffUtilCallback : DiffUtil.ItemCallback<SimilarMoviesItemImpl>() {
    override fun areItemsTheSame(
        oldItem: SimilarMoviesItemImpl,
        newItem: SimilarMoviesItemImpl
    ): Boolean = oldItem.filmId == newItem.filmId


    override fun areContentsTheSame(
        oldItem: SimilarMoviesItemImpl,
        newItem: SimilarMoviesItemImpl
    ): Boolean = oldItem == newItem

}

class SimilarMovieViewHolder(val binding: FilmListItemBinding) :
    RecyclerView.ViewHolder(binding.root)
