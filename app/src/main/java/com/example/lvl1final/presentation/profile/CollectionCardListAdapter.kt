package com.example.lvl1final.presentation.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lvl1final.databinding.CollectionCardListItemBinding
import com.example.lvl1final.domain.models.collection.MoviesCollection


class CollectionCardListAdapter(
    private val onCollectionItemClick: (moviesCollection: MoviesCollection) -> Unit,
    private val deleteCollectionButtonClick: (moviesCollection: MoviesCollection) -> Unit
) : ListAdapter<MoviesCollection, CollectionCardViewHolder>(CollectionCardDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionCardViewHolder {
        val binding =
            CollectionCardListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CollectionCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollectionCardViewHolder, position: Int) {
        val item = getItem(position)

        item.apply {
            holder.binding.root.setOnClickListener {
                onCollectionItemClick(this)
            }

            if (deletable) {
                holder.binding.imageViewDeleteCollection.setOnClickListener {
                    deleteCollectionButtonClick(this)
                }
            } else {
                holder.binding.imageViewDeleteCollection.visibility = View.GONE
            }


            holder.binding.apply {
                if (collectionIcon != null) {
                    imgViewCollectionIcon.setImageResource(collectionIcon)
                }

                textviewCollectionName.text = collectionName
                textviewNumberOfMovies.text = numberOfMovies.toString()
            }
        }
    }
}

class CollectionCardDiffUtilCallback : DiffUtil.ItemCallback<MoviesCollection>() {
    override fun areItemsTheSame(oldItem: MoviesCollection, newItem: MoviesCollection): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: MoviesCollection, newItem: MoviesCollection): Boolean =
        oldItem == newItem

}

class CollectionCardViewHolder(val binding: CollectionCardListItemBinding) :
    RecyclerView.ViewHolder(binding.root)
