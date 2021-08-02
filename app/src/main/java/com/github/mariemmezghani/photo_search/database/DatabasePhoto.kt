package com.github.mariemmezghani.photo_search.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
class DatabasePhoto constructor (@PrimaryKey val id: String, val url: String, val title: String)

