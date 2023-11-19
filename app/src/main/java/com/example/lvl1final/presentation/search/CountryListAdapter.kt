package com.example.lvl1final.presentation.search

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lvl1final.R
import com.example.lvl1final.data.api.CountryDto
import com.example.lvl1final.databinding.CountryGenreListItemBinding

class CountryListAdapter(
    private val setCountryTempValues: (countryDto: CountryDto) -> Unit
) : ListAdapter<CountryDto, CountryViewHolder>(CountryDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding =
            CountryGenreListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val context = holder.binding.root.context
        val item = getItem(position)

        item?.apply {
            holder.binding.apply {
                if (country == "") {
                    tvCountryGenre.setTypeface(null, Typeface.BOLD)
                    tvCountryGenre.text = context.getString(R.string.all)
                } else {
                    tvCountryGenre.text = country.replaceFirstChar { it.uppercase() }
                }
            }

            holder.binding.root.setOnClickListener {
                setCountryTempValues(this)
            }
        }
    }
}

class CountryDiffUtilCallback : DiffUtil.ItemCallback<CountryDto>() {
    override fun areItemsTheSame(oldItem: CountryDto, newItem: CountryDto): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CountryDto, newItem: CountryDto): Boolean =
        oldItem == newItem
}

class CountryViewHolder(val binding: CountryGenreListItemBinding) :
    RecyclerView.ViewHolder(binding.root)
