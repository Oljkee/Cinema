package com.example.lvl1final.presentation.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lvl1final.databinding.FilmListItemBinding
import com.example.lvl1final.data.entity.InterestingMovieWithKinopoiskMovie

class InterestingMovieListAdapter(
    private val onItemClick: (id: Int) -> Unit
) : ListAdapter<InterestingMovieWithKinopoiskMovie, InterestingMovieViewHolder>(
    InterestingMovieDiffUtilCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestingMovieViewHolder {
        val binding =
            FilmListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InterestingMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InterestingMovieViewHolder, position: Int) {
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

class InterestingMovieDiffUtilCallback :
    DiffUtil.ItemCallback<InterestingMovieWithKinopoiskMovie>() {
    override fun areItemsTheSame(
        oldItem: InterestingMovieWithKinopoiskMovie,
        newItem: InterestingMovieWithKinopoiskMovie
    ): Boolean = oldItem.interestingMovie.id == newItem.interestingMovie.id

    override fun areContentsTheSame(
        oldItem: InterestingMovieWithKinopoiskMovie,
        newItem: InterestingMovieWithKinopoiskMovie
    ): Boolean = oldItem == newItem

}

class InterestingMovieViewHolder(val binding: FilmListItemBinding) :
    RecyclerView.ViewHolder(binding.root)
