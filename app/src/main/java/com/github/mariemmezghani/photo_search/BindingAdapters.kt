package com.github.mariemmezghani.photo_search

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.mariemmezghani.photo_search.model.Photo
import com.squareup.picasso.Picasso

@BindingAdapter("photo")
fun bindPhoto(imageView: ImageView, url: String?) {
    url?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()

        Picasso.get()
            .load(imgUri)
            .resize(400, 400)
            .centerCrop()
            .into(imageView)
    }
}
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Photo>?) {
    val adapter = recyclerView.adapter as FavoritesAdapter
    adapter.submitList(data)
}


