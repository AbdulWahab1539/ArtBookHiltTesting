package com.abduldev.artbookapptestingguide.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.abduldev.artbookapptestingguide.R
import com.abduldev.artbookapptestingguide.databinding.FragmentArtDetailBinding
import com.abduldev.artbookapptestingguide.utils.Status
import com.abduldev.artbookapptestingguide.viewmodels.ArtViewModel
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtDetailFragment @Inject constructor(
    private val glide: RequestManager
) : Fragment(R.layout.fragment_art_detail) {


    private lateinit var binding: FragmentArtDetailBinding
    lateinit var viewModel: ArtViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentArtDetailBinding.bind(view)
        viewModel = ViewModelProvider(
            requireActivity()
        )[ArtViewModel::class.java]

        binding.ivArtistProfile.setOnClickListener {
            findNavController()
                .navigate(
                    ArtDetailFragmentDirections
                        .actionArtDetailFragmentToSearchFragment()
                )
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.btnSave.setOnClickListener {
            viewModel.validateInputs(
                binding.etName.text.toString(),
                binding.etArtist.text.toString(),
                binding.etYear.text.toString()
            )
        }

        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        viewModel.selectedImageUrl.observe(viewLifecycleOwner) {
            glide.load(it).into(binding.ivArtistProfile)
        }

        viewModel.insertArtMessage.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(
                        requireActivity(),
                        "Success",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().popBackStack()
                    viewModel.resetArtMsg()
                }
                Status.ERROR -> {
                    Toast.makeText(
                        requireActivity(),
                        it.message ?: "Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Status.LOADING -> {

                }
            }
        }
    }

}