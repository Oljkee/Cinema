package com.example.lvl1final.presentation.search

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lvl1final.R
import com.example.lvl1final.databinding.CountryGenreListItemBinding
import com.example.lvl1final.domain.models.movieimpl.GenreImpl

class GenreListAdapter(
    private val setGenreTempValues: (genre: GenreImpl) -> Unit
) : ListAdapter<GenreImpl, GenreViewHolder>(GenreDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding =
            CountryGenreListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val context = holder.binding.root.context
        val item = getItem(position)

        item?.apply {
            holder.binding.apply {
                if (genre == "") {
                    tvCountryGenre.setTypeface(null, Typeface.BOLD)
                    tvCountryGenre.text = context.getString(R.string.all)
                } else {
                    tvCountryGenre.text = genre.replaceFirstChar { it.uppercase() }
                }
            }


            holder.binding.root.setOnClickListener {
                setGenreTempValues(this)
            }
        }
    }
}

class GenreDiffUtilCallback : DiffUtil.ItemCallback<GenreImpl>() {
    override fun areItemsTheSame(oldItem: GenreImpl, newItem: GenreImpl): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: GenreImpl, newItem: GenreImpl): Boolean =
        oldItem == newItem
}

class GenreViewHolder(val binding: CountryGenreListItemBinding) :
    RecyclerView.ViewHolder(binding.root)
