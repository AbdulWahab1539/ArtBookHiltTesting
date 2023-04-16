package com.abduldev.artbookapptestingguide.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abduldev.artbookapptestingguide.R
import com.abduldev.artbookapptestingguide.db.enities.Art
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ArtsAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ArtsAdapter.ArtsViewHolder>() {

    inner class ArtsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private val callback = object : DiffUtil.ItemCallback<Art>() {
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem.Id == newItem.Id
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, callback)

    var arts: List<Art>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ArtsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.art_row,
            parent,
            false
        )
        return ArtsViewHolder(view)
    }

    override fun getItemCount(): Int = arts.size

    override fun onBindViewHolder(holder: ArtsViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.ivArt)
        val name = holder.itemView.findViewById<TextView>(R.id.artName)
        val artistName = holder.itemView.findViewById<TextView>(R.id.artArtist)
        val year = holder.itemView.findViewById<TextView>(R.id.artYear)
        val art = arts[position]
        holder.itemView.apply {
            name.text = "Name: ${art.name}"
            artistName.text = "Artist Name: ${art.artistName}"
            year.text = "Year: ${art.year}"
            glide.load(art.imageUrl).into(imageView)
        }


    }
}