package com.abduldev.artbookapptestingguide.di

import android.content.Context
import androidx.room.Room
import com.abduldev.artbookapptestingguide.db.ArtistDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("testDatabase")
    fun provideInMemoryRoom(
        @ApplicationContext context: Context
    ) = Room.inMemoryDatabaseBuilder(
        context, ArtistDatabase::class.java
    ).allowMainThreadQueries().build()
}