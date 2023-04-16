package com.abduldev.artbookapptestingguide.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abduldev.artbookapptestingguide.db.dao.ArtDao
import com.abduldev.artbookapptestingguide.db.enities.Art


@Database(
    entities = [Art::class],
    version = 1
)
abstract class ArtistDatabase() : RoomDatabase() {
    abstract fun artDao(): ArtDao
}