package com.github.mariemmezghani.photo_search.network

import com.github.mariemmezghani.photo_search.BuildConfig
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = BuildConfig.API_KEY

interface ApiService {

    @GET("?method=flickr.photos.search&api_key=$API_KEY&format=json&nojsoncallback=1&safe_search=1")
    @Wrapped(path = ["photos", "photo"])
    suspend fun getSearchedPhotos(
        @Query(value = "text") searchTerm: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): List<PhotoResponse>

    companion object PhotoApi {
        private const val BASE_URL = "https://api.flickr.com/services/rest/"
        fun create(): ApiService {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .add(Wrapped.ADAPTER_FACTORY)
                .build()

            val retrofit =
                Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
                    .baseUrl(BASE_URL).build()
            return retrofit.create(ApiService::class.java)
        }

    }


}

