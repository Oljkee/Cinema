package com.example.lvl1final.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lvl1final.R
import com.example.lvl1final.data.api.EpisodeDto
import com.example.lvl1final.databinding.SerialEpisodeListItemBinding
import java.text.SimpleDateFormat
import java.util.*

class EpisodesListAdapter :
    ListAdapter<EpisodeDto, EpisodesViewHolder>(EpisodesDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
        val binding =
            SerialEpisodeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        val context = holder.binding.root.context
        val item = getItem(position)

        item?.apply {
            val name = nameRu ?: nameEn
            val episodeNumberAndName = context.resources.getString(
                R.string.episodeNumberAndName,
                item.episodeNumber,
                name
            )

            val date = if (!releaseDate.isNullOrEmpty()) {
                formatDate(releaseDate)
            } else ""

            holder.binding.apply {
                textviewEpisodeNumberName.text = episodeNumberAndName
                textviewReleaseDate.text = date
            }
        }
    }

    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date)
    }
}

class EpisodesDiffUtilCallback : DiffUtil.ItemCallback<EpisodeDto>() {
    override fun areItemsTheSame(oldItem: EpisodeDto, newItem: EpisodeDto): Boolean =
        oldItem.episodeNumber == newItem.episodeNumber

    override fun areContentsTheSame(oldItem: EpisodeDto, newItem: EpisodeDto): Boolean =
        oldItem == newItem
}

class EpisodesViewHolder(val binding: SerialEpisodeListItemBinding) :
    RecyclerView.ViewHolder(binding.root)
