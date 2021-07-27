package com.github.mariemmezghani.photo_search.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(val id: String, val url: String, val title: String) : Parcelable