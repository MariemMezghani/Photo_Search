package com.github.mariemmezghani.photo_search

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.mariemmezghani.photo_search.model.Photo

class DetailViewModelFactory (private val application: Application, private val photo: Photo) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                return DetailViewModel(application,photo) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}
