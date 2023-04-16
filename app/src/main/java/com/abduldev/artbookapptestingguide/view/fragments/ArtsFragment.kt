package com.abduldev.artbookapptestingguide.view.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abduldev.artbookapptestingguide.R
import com.abduldev.artbookapptestingguide.adapters.ArtsAdapter
import com.abduldev.artbookapptestingguide.databinding.FragmentArtsBinding
import com.abduldev.artbookapptestingguide.viewmodels.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtsFragment @Inject constructor(
    private val artsAdapter: ArtsAdapter
) : Fragment(R.layout.fragment_arts) {

    private lateinit var binding: FragmentArtsBinding
    lateinit var artViewModel: ArtViewModel

    private val swipeCallback = object : ItemTouchHelper.SimpleCallback
        (
        0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(
            viewHolder: RecyclerView.ViewHolder, direction: Int
        ) {
            val position = viewHolder.layoutPosition
            val selectedArt = artsAdapter.arts[position]
            artViewModel.deleteArt(selectedArt)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        artViewModel = ViewModelProvider(
            requireActivity()
        )[ArtViewModel::class.java]

        binding = FragmentArtsBinding.bind(view)

        binding.floatingActionButton.setOnClickListener {
            findNavController()
                .navigate(
                    ArtsFragmentDirections
                        .actionArtsFragmentToArtDetailFragment()
                )
        }

        binding.rvArts.apply {
            adapter = artsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        ItemTouchHelper(swipeCallback)
            .attachToRecyclerView(binding.rvArts)
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        artViewModel.artList.observe(
            viewLifecycleOwner
        ) {
            artsAdapter.arts = it
        }
    }
}