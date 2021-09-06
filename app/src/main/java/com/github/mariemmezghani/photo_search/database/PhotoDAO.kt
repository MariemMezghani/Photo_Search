package com.github.mariemmezghani.photo_search.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.github.mariemmezghani.photo_search.model.Photo

@Dao
interface PhotoDAO {

    // get all saved photos
    @Query("select * FROM favorites_table")
    fun getPhotos(): LiveData<List<Photo>>

    // insert a favorite photo to database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: Photo)

    // delete a favorite photo to database
    @Delete
    suspend fun delete(photo: Photo)
}