package com.github.mariemmezghani.photo_search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.mariemmezghani.photo_search.databinding.PhotoItemViewBinding
import com.github.mariemmezghani.photo_search.model.Photo


class FavoritesAdapter: ListAdapter<Photo, FavoritesAdapter.FavoritesViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesAdapter.FavoritesViewHolder {
        return FavoritesViewHolder(PhotoItemViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FavoritesAdapter.FavoritesViewHolder, position: Int) {
        val photo= getItem(position)
        holder.bind(photo)

    }
    companion object DiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }
    }
    class FavoritesViewHolder(private var binding:PhotoItemViewBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(photo:Photo){
            binding.photo=photo
            binding.executePendingBindings()
        }

    }
}