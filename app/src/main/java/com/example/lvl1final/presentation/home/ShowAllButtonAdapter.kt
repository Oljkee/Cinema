package com.example.lvl1final.presentation.home

import com.example.lvl1final.databinding.RecyclerviewButtonLastItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ShowAllButtonAdapter(
    private val onItemClick: () -> Unit
) : RecyclerView.Adapter<ShowAllButtonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowAllButtonViewHolder {
        val binding =
            RecyclerviewButtonLastItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ShowAllButtonViewHolder(binding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: ShowAllButtonViewHolder, position: Int) {
        holder.binding.root.setOnClickListener {
            onItemClick()
        }
    }
}

class ShowAllButtonViewHolder(val binding: RecyclerviewButtonLastItemBinding) :
    RecyclerView.ViewHolder(binding.root)
