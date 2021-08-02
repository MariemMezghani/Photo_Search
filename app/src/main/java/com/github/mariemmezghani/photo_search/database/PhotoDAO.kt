package com.github.mariemmezghani.photo_search.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PhotoDAO {

     // get all saved photos
    @Query("select * FROM favorites_table")
    fun getPhotos(): List<DatabasePhoto>

    // insert a favorite photo to database
    @Insert
    fun insert(photo: DatabasePhoto)
}