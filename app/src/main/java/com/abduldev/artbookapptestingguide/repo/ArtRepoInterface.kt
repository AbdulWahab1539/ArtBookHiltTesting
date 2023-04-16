package com.abduldev.artbookapptestingguide.repo

import androidx.lifecycle.LiveData
import com.abduldev.artbookapptestingguide.api.models.ImageResponse
import com.abduldev.artbookapptestingguide.db.enities.Art
import com.abduldev.artbookapptestingguide.utils.Resource

interface ArtRepoInterface {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getAllArts(): LiveData<List<Art>>

    suspend fun searchImage(imageString: String): Resource<ImageResponse>
}