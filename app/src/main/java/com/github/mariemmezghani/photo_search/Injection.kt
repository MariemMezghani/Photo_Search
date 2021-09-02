package com.github.mariemmezghani.photo_search

import androidx.lifecycle.ViewModelProvider
import com.github.mariemmezghani.photo_search.database.PhotoDAO
import com.github.mariemmezghani.photo_search.database.PhotoDatabase
import com.github.mariemmezghani.photo_search.network.ApiService
import com.github.mariemmezghani.photo_search.repository.PhotosRepository

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */

object Injection {
    /**
     * Creates an instance of [PhotosRepository] based on the [ApiService]
     */
    fun providePhotosRepository(photodb:PhotoDAO): PhotosRepository {
        return PhotosRepository(ApiService.create(), photodb)
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [MainViewModel] objects.
     */
    fun provideViewModelFactory(photodb: PhotoDAO): ViewModelProvider.Factory {
        return MainViewModelFactory(providePhotosRepository(photodb))
    }
}