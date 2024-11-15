package com.example.lvl1final.presentation.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lvl1final.databinding.StaffMemberFilmographyListItemBinding
import com.example.lvl1final.domain.models.movieimpl.FilmImpl

class FilmographyMovieListAdapter(
    private val onItemClick: (film: FilmImpl) -> Unit
) : ListAdapter<FilmImpl, FilmographyMovieViewHolder>(FilmographyDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmographyMovieViewHolder {
        val binding =
            StaffMemberFilmographyListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return FilmographyMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmographyMovieViewHolder, position: Int) {
        val context = holder.binding.root.context
        val item = getItem(position)

        item.apply {
            holder.binding.root.setOnClickListener {
                onItemClick(this)
            }
            val name = nameRu ?: nameEn

            val photoUrl =
                "https://kinopoiskapiunofficial.tech/images/posters/kp_small/${filmId}.jpg"

            holder.binding.apply {
                Glide.with(context)
                    .load(photoUrl)
                    .into(imgViewMoviePoster)

                textviewMovieName.text = name
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

class FilmographyDiffUtilCallback : DiffUtil.ItemCallback<FilmImpl>() {
    override fun areItemsTheSame(oldItem: FilmImpl, newItem: FilmImpl): Boolean =
        oldItem.filmId == newItem.filmId

    override fun areContentsTheSame(oldItem: FilmImpl, newItem: FilmImpl): Boolean =
        oldItem == newItem
}

class FilmographyMovieViewHolder(val binding: StaffMemberFilmographyListItemBinding) :
    RecyclerView.ViewHolder(binding.root)
