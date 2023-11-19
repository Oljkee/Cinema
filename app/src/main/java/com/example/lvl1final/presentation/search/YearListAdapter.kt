package com.example.lvl1final.presentation.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lvl1final.R
import com.example.lvl1final.databinding.YearListItemBinding

class YearListAdapter(
    private val setNewYear: (year: String) -> Unit,
    private var selectedYear: Int
) : ListAdapter<Int, YearViewHolder>(YearDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearViewHolder {
        val binding =
            YearListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return YearViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: YearViewHolder, position: Int) {
        val context = holder.binding.root.context
        val year = getItem(position)

        holder.binding.apply {
            tvYear.text = year.toString()
            root.setOnClickListener {
                selectedYear = year!!
                notifyDataSetChanged()
                setNewYear(year.toString())
            }

            if (year == selectedYear) {
                tvYear.setTextColor(context.getColor(R.color.app_theme_blue))
            } else {
                tvYear.setTextColor(context.getColor(R.color.app_theme_black))
            }
        }
    }
}

class YearDiffUtilCallback : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem
}

class YearViewHolder(val binding: YearListItemBinding) : RecyclerView.ViewHolder(binding.root)
