package com.abduldev.artbookapptestingguide.repo

import androidx.lifecycle.LiveData
import com.abduldev.artbookapptestingguide.api.models.ImageResponse
import com.abduldev.artbookapptestingguide.api.RetrofitApi
import com.abduldev.artbookapptestingguide.db.dao.ArtDao
import com.abduldev.artbookapptestingguide.db.enities.Art
import com.abduldev.artbookapptestingguide.utils.Resource
import javax.inject.Inject

class ArtRepo @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitApi: RetrofitApi
) : ArtRepoInterface {

    override suspend fun insertArt(art: Art) {
        artDao.insertAtr(art = art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art = art)
    }

    override fun getAllArts(): LiveData<List<Art>> {
        return artDao.getAllArts()
    }

    override suspend fun searchImage(
        imageString: String
    ): Resource<ImageResponse> {
        return try {
            val response = retrofitApi.imageSearch(searchQuery = imageString)
            if (response.isSuccessful) {
                response.body()?.let { res ->
                    return@let Resource.success(res)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No Data", null)
        }

    }
}