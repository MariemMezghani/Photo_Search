package com.github.mariemmezghani.photo_search.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "favorites_table")
data class Photo(@PrimaryKey val id: String, val url: String, val title: String) : Parcelable