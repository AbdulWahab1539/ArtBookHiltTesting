package com.abduldev.artbookapptestingguide.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.abduldev.artbookapptestingguide.adapters.ArtsAdapter
import com.abduldev.artbookapptestingguide.adapters.ImageAdapter
import com.abduldev.artbookapptestingguide.view.fragments.ArtDetailFragment
import com.abduldev.artbookapptestingguide.view.fragments.ArtsFragment
import com.abduldev.artbookapptestingguide.view.fragments.SearchFragment
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ArtFragmentFactory @Inject
constructor(
    private val glide: RequestManager,
    private val artsAdapter: ArtsAdapter,
    private val imageAdapter: ImageAdapter
) : FragmentFactory() {

    override fun instantiate(
        classLoader: ClassLoader, className: String
    ): Fragment {
        return when (className) {
            ArtsFragment::class.java.name -> ArtsFragment(artsAdapter)
            SearchFragment::class.java.name -> SearchFragment(imageAdapter)
            ArtDetailFragment::class.java.name -> ArtDetailFragment(glide)
            else -> return super.instantiate(classLoader, className)
        }

    }
}