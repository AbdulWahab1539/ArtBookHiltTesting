package com.abduldev.artbookapptestingguide.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abduldev.artbookapptestingguide.api.models.ImageResponse
import com.abduldev.artbookapptestingguide.db.enities.Art
import com.abduldev.artbookapptestingguide.utils.Resource

class FakeArtRepoTest : ArtRepoInterface {
    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>()


    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshData()
    }

    override fun getAllArts(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun searchImage(imageString: String):
            Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(), 0, 0))
    }

    private fun refreshData(){
        artsLiveData.postValue(arts)
    }

}