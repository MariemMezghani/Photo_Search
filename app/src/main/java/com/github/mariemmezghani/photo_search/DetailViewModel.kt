package com.github.mariemmezghani.photo_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mariemmezghani.photo_search.model.Photo

class DetailViewModel (photo: Photo) : ViewModel(){

    private val _selectedPhoto = MutableLiveData<Photo>()
    val selectedPhoto: LiveData<Photo>
    get() = _selectedPhoto

    init {
        _selectedPhoto.value=photo
    }

}