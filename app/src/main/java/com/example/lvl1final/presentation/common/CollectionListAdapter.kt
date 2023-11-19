package com.example.lvl1final.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lvl1final.data.entity.Collection
import com.example.lvl1final.databinding.CollectionListItemBinding


class CollectionListAdapter(
    private val onCollectionItemClick: (moviesCollection: Collection, isChecked: Boolean) -> Unit
) : ListAdapter<Collection, CollectionViewHolder>(CollectionDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val binding =
            CollectionListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CollectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val item = getItem(position)

        item.apply {
            holder.binding.apply {
                checkBoxSelectCollection.isChecked = isMovieInCollection

                checkBoxSelectCollection.text = collectionName
                textviewNumberOfMovies.text = numberOfMovies.toString()

                root.setOnClickListener {
                    onCollectionItemClick(item, isMovieInCollection)
                }
            }
        }
    }
}


class CollectionDiffUtilCallback : DiffUtil.ItemCallback<Collection>() {
    override fun areItemsTheSame(oldItem: Collection, newItem: Collection): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Collection, newItem: Collection): Boolean =
        oldItem == newItem

}

class CollectionViewHolder(val binding: CollectionListItemBinding) :
    RecyclerView.ViewHolder(binding.root)
