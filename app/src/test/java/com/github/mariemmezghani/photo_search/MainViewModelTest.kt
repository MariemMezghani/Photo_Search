package com.github.mariemmezghani.photo_search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.mariemmezghani.photo_search.network.ApiService
import com.github.mariemmezghani.photo_search.repository.PhotosRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
    class MainViewModelTest{
        @get:Rule
        val instantTaskExecutorRule = InstantTaskExecutorRule()
        @Mock
        lateinit var mainViewModel: MainViewModel
        lateinit var repository:PhotosRepository
        val api:ApiService= ApiService.create()
        @Before
        fun setUp() {
            MockitoAnnotations.initMocks(this)
            this.repository= PhotosRepository(this.api)
            this.mainViewModel = MainViewModel(this.repository)
        }
        @Test
        fun getPhotosTest(){
            //Invoke
            mainViewModel.getPhotosList("nature")
            // verify
            assertNotNull(this.mainViewModel.photos)
        }
    }
