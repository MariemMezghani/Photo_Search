package com.github.mariemmezghani.photo_search.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.github.mariemmezghani.photo_search.database.PhotoDAO
import com.github.mariemmezghani.photo_search.model.Photo
import com.github.mariemmezghani.photo_search.model.PhotoPagingSource
import com.github.mariemmezghani.photo_search.network.ApiService

class PhotosRepository(private val service: ApiService, private val database: PhotoDAO) {
    fun getPhotos(query: String) = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { PhotoPagingSource(service, query) }
    ).liveData

    val favorites:LiveData<List<Photo>> = database.getPhotos()
}                 
