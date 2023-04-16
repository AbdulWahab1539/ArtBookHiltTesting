package com.abduldev.artbookapptestingguide.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.abduldev.artbookapptestingguide.R
import com.abduldev.artbookapptestingguide.adapters.ImageAdapter
import com.abduldev.artbookapptestingguide.api.models.ImageResponse
import com.abduldev.artbookapptestingguide.databinding.FragmentSearchBinding
import com.abduldev.artbookapptestingguide.utils.Status
import com.abduldev.artbookapptestingguide.viewmodels.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment @Inject constructor(
    val imageAdapter: ImageAdapter
) : Fragment(R.layout.fragment_search) {
    lateinit var artViewModel: ArtViewModel

    lateinit var binding: FragmentSearchBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchBinding.bind(view)
        artViewModel = ViewModelProvider(
            requireActivity()
        )[ArtViewModel::class.java]

        binding.rvSearch.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }

        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            artViewModel.setSelectedImage(it)
        }
        var job: Job? = null
        binding.etSearch.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        artViewModel.searchForImage(it.toString())
                    }
                }
            }
        }
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        artViewModel.imageList.observe(
            viewLifecycleOwner,
        ) {
            when (it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { hit ->
                        hit.previewURL
                    }

                    imageAdapter.images = urls ?: listOf()
                    binding.progressSearch.visibility = View.GONE
                }

                Status.LOADING -> {
                    binding.progressSearch.visibility = View.VISIBLE
                }

                Status.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        it.message ?: "Error",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressSearch.visibility = View.GONE
                }
            }
        }
    }
}