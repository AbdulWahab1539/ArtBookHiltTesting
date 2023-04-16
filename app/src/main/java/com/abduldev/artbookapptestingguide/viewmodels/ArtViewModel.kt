package com.abduldev.artbookapptestingguide.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abduldev.artbookapptestingguide.api.models.ImageResponse
import com.abduldev.artbookapptestingguide.db.enities.Art
import com.abduldev.artbookapptestingguide.repo.ArtRepoInterface
import com.abduldev.artbookapptestingguide.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val repoInterface: ArtRepoInterface
) : ViewModel() {

    //Art Fragment
    val artList = repoInterface.getAllArts()

    // Image API Fragment
    private val images = MutableLiveData<Resource<ImageResponse>>()

    val imageList: LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()

    val selectedImageUrl: LiveData<String>
        get() = selectedImage


    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage: LiveData<Resource<Art>>
        get() = insertArtMsg

    fun resetArtMsg() {
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url: String) {
        selectedImage.postValue(url)
    }


    fun deleteArt(art: Art) = viewModelScope.launch {
        repoInterface.deleteArt(art)
    }

    private fun insertArt(art: Art) = viewModelScope.launch {
        repoInterface.insertArt(art)
    }

    fun searchForImage(searchString: String) {
        if (searchString.isEmpty()) {
            return
        }
        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repoInterface.searchImage(searchString)
            images.value = response
        }
    }

    fun validateInputs(name: String, artist: String, year: String) {
        if (name.isEmpty() || artist.isEmpty() || year.isEmpty()) {
            insertArtMsg.postValue(
                Resource.error(
                    "Enter name, artist, year", null
                )
            )
            return
        }
        val yearInt = try {
            year.toInt()
        } catch (e: Exception) {
            insertArtMsg.postValue(
                Resource.error("Year should be number", null)
            )
            return
        }
        val art = Art(
            name = name,
            artistName = artist,
            year = yearInt,
            selectedImage.value ?: ""
        )

        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))

    }

}