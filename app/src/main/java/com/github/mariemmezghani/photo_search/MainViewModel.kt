package com.github.mariemmezghani.photo_search

import androidx.lifecycle.*
import com.github.mariemmezghani.photo_search.repository.PhotosRepository
import androidx.paging.cachedIn
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.switchMap
import androidx.lifecycle.ViewModel
import com.github.mariemmezghani.photo_search.model.Photo

 //lateinit var database:PhotoDatabase
 //private val repository= PhotosRepository(database = database)
class MainViewModel(private val repository: PhotosRepository) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    /**
     * Variable that tells the Fragment to navigate to [DetailFragment]
     * This is private because we do not want to set this value to the Fragment
     */
    private val _navigate = MutableLiveData<Photo?>()

    /**
     * If this is not null, immediately navigate to [DetailFragment]
     * and then call navigationCompleted
     */
    val navigate: LiveData<Photo?>
        get() = _navigate

    val photos = currentQuery.switchMap { queryString ->
        repository.getPhotos(queryString).cachedIn(viewModelScope)
    }
     val favorites=repository.favorites

    init {
        getPhotosList(currentQuery.value ?: "")
    }


    fun getPhotosList(query: String) {
        currentQuery.value = query

    }

    fun onItemClicked(photo: Photo) {
        _navigate.value = photo
    }

    fun navigationCompleted() {
        _navigate.value = null
    }


    companion object {
        private const val DEFAULT_QUERY = "nature"
    }

}