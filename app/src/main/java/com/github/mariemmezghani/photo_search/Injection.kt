package com.github.mariemmezghani.photo_search

import android.app.Application
import androidx.lifecycle.ViewModelProvider
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
    fun providePhotosRepository(photodb:PhotoDatabase?): PhotosRepository {
        return PhotosRepository(ApiService.create(), photodb)
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [MainViewModel] objects.
     */
    fun provideViewModelFactory(photodb: PhotoDatabase?): ViewModelProvider.Factory {
        return MainViewModelFactory(providePhotosRepository(photodb))
    }
}