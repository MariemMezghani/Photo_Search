package com.github.mariemmezghani.photo_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.mariemmezghani.photo_search.model.Photo

class DetailViewModelFactory (private val photo: Photo) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                return DetailViewModel(photo) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}
