package com.example.lvl1final.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lvl1final.databinding.StaffMemberFilmographyListItemBinding
import com.example.lvl1final.domain.models.movieimpl.MovieImpl

class SearchMoviePagingDataAdapter(
    private val onItemClick: (movie: MovieImpl) -> Unit
) : PagingDataAdapter<MovieImpl, SearchMovieViewHolder>(SearchDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder {
        val binding =
            StaffMemberFilmographyListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return SearchMovieViewHolder(binding)
    }


    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {
        val context = holder.binding.root.context
        val item = getItem(position)

        item?.apply {
            holder.binding.root.setOnClickListener {
                onItemClick(this)
            }
            val name = nameRu ?: (nameOriginal ?: nameEn)
            val photoUrl = posterUrlPreview
            val genre: String = genres.joinToString(separator = ", ") { genreDto ->
                genreDto.genre
            }
            val yearAndGenre = "$year, $genre"

            holder.binding.apply {
                Glide.with(context)
                    .load(photoUrl)
                    .into(imgViewMoviePoster)

                textviewMovieName.text = name
                textviewYearGenre.text = yearAndGenre
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

class SearchDiffUtilCallback : DiffUtil.ItemCallback<MovieImpl>() {
    override fun areItemsTheSame(oldItem: MovieImpl, newItem: MovieImpl): Boolean =
        oldItem.filmId == newItem.filmId

    override fun areContentsTheSame(oldItem: MovieImpl, newItem: MovieImpl): Boolean =
        oldItem == newItem
}

class SearchMovieViewHolder(val binding: StaffMemberFilmographyListItemBinding) :
    RecyclerView.ViewHolder(binding.root)
