package com.example.lvl1final.presentation.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lvl1final.databinding.FilmListItemBinding
import com.example.lvl1final.domain.models.movieimpl.FilmImpl

class ActorMovieListAdapter(
    private val onItemClick: (film: FilmImpl) -> Unit,
    private val isWatchedMovie: (id: Int) -> Boolean
) : ListAdapter<FilmImpl, ActorMovieViewHolder>(ActorDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorMovieViewHolder {
        val binding =
            FilmListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorMovieViewHolder, position: Int) {
        val context = holder.binding.root.context
        val item = getItem(position)


        item.apply {
            holder.binding.root.setOnClickListener {
                onItemClick(this)
            }

            val photoUrl =
                "https://kinopoiskapiunofficial.tech/images/posters/kp_small/${filmId}.jpg"
            val name = nameRu ?: nameEn

            holder.binding.apply {
                watchedMovieGradientBackground.visibility = if (isWatchedMovie(filmId))
                    View.VISIBLE
                else View.GONE

                Glide.with(context)
                    .load(photoUrl)
                    .into(imgViewPoster)

                textviewName.text = name

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

class ActorDiffUtilCallback : DiffUtil.ItemCallback<FilmImpl>() {
    override fun areItemsTheSame(oldItem: FilmImpl, newItem: FilmImpl): Boolean =
        oldItem.filmId == newItem.filmId


    override fun areContentsTheSame(oldItem: FilmImpl, newItem: FilmImpl): Boolean =
        oldItem == newItem

}

class ActorMovieViewHolder(val binding: FilmListItemBinding) : RecyclerView.ViewHolder(binding.root)
