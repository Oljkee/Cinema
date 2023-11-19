package com.example.lvl1final.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lvl1final.data.api.MovieStaffDto
import com.example.lvl1final.databinding.StaffMemberListItemBinding
import com.example.lvl1final.presentation.Arguments

class MovieStaffListAdapter(
    private val onActorItemClick: (staffMember: MovieStaffDto) -> Unit
) : ListAdapter<MovieStaffDto, MovieStaffViewHolder>(StaffDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieStaffViewHolder {
        val binding =
            StaffMemberListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieStaffViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieStaffViewHolder, position: Int) {
        val context = holder.binding.root.context
        val item = getItem(position)

        item.apply {
            holder.binding.root.setOnClickListener {
                onActorItemClick(this)
            }

            val photoUrl = posterUrl
            val name = if (nameRu == "") nameEn else nameRu
            val description =
                if (professionKey == Arguments.MOVIE_STAFF_PROFESSION_ACTOR) description
                else professionText

            holder.binding.apply {
                Glide.with(context)
                    .load(photoUrl)
                    .into(imgViewAvatar)

                textviewName.text = name
                textviewDescription.text = description
            }
        }
    }
}

class StaffDiffUtilCallback : DiffUtil.ItemCallback<MovieStaffDto>() {
    override fun areItemsTheSame(oldItem: MovieStaffDto, newItem: MovieStaffDto): Boolean =
        oldItem.staffId == newItem.staffId

    override fun areContentsTheSame(oldItem: MovieStaffDto, newItem: MovieStaffDto): Boolean =
        oldItem == newItem

}

class MovieStaffViewHolder(val binding: StaffMemberListItemBinding) :
    RecyclerView.ViewHolder(binding.root)
