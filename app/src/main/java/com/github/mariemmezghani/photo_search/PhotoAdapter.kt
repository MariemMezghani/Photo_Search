package com.github.mariemmezghani.photo_search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.github.mariemmezghani.photo_search.model.Photo
import androidx.recyclerview.widget.RecyclerView
import com.github.mariemmezghani.photo_search.databinding.PhotoItemViewBinding


class PhotoAdapter : PagingDataAdapter<Photo, PhotoAdapter.PhotoViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            PhotoAdapter.PhotoViewHolder {
        return PhotoViewHolder(PhotoItemViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PhotoAdapter.PhotoViewHolder, position: Int) {
        val photo = getItem(position)
        if (photo != null) {
            holder.bind(photo)
        }

    }

    class PhotoViewHolder(private var binding: PhotoItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.photo = photo

        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }
    }
}