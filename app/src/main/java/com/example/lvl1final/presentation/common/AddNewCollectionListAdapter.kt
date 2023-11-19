package com.example.lvl1final.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.example.lvl1final.databinding.AddNewCollectionBinding

class AddNewCollectionListAdapter(
    private val onClick: () -> Unit
) : RecyclerView.Adapter<AddNewCollectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddNewCollectionViewHolder {
        val binding =
            AddNewCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddNewCollectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddNewCollectionViewHolder, position: Int) {
        holder.binding.root.setOnClickListener {
            onClick()
        }
        holder.binding.linearLayoutCreateCollection.setPadding(0)
    }

    override fun getItemCount(): Int {
        return 1
    }
}

class AddNewCollectionViewHolder(val binding: AddNewCollectionBinding) :
    RecyclerView.ViewHolder(binding.root)
