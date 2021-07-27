package com.github.mariemmezghani.photo_search.network

import com.github.mariemmezghani.photo_search.model.Photo

data class PhotoResponse(
    val id: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String
)

fun List<PhotoResponse>.asDomainModel(): List<Photo> {
    return map {
        Photo(
            it.id,
            "https://farm${it.farm}.staticflickr.com/${it.server}/${it.id}_${it.secret}.jpg",
            it.title

        )
    }
}