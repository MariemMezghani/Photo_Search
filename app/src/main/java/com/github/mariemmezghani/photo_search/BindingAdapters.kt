package com.github.mariemmezghani.photo_search

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
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




