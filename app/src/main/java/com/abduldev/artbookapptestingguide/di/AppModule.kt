package com.abduldev.artbookapptestingguide.di

import android.content.Context
import androidx.room.Room
import com.abduldev.artbookapptestingguide.R
import com.abduldev.artbookapptestingguide.api.RetrofitApi
import com.abduldev.artbookapptestingguide.db.ArtistDatabase
import com.abduldev.artbookapptestingguide.db.dao.ArtDao
import com.abduldev.artbookapptestingguide.repo.ArtRepo
import com.abduldev.artbookapptestingguide.repo.ArtRepoInterface
import com.abduldev.artbookapptestingguide.utils.Constants.BASE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ) =
        Room.databaseBuilder(
            context,
            ArtistDatabase::class.java,
            "ArtBookDB"
        ).build()

    @Provides
    @Singleton
    fun provideArtDao(database: ArtistDatabase) = database.artDao()


    @Provides
    @Singleton
    fun provideRetrofit(): RetrofitApi =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitApi::class.java)

    @Provides
    @Singleton
    fun provideGlide(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
    )


    @Provides
    @Singleton
    fun provideRealRepo(artDao: ArtDao, retrofitApi: RetrofitApi) =
        ArtRepo(artDao, retrofitApi) as ArtRepoInterface
}