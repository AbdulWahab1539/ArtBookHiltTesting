package com.abduldev.artbookapptestingguide.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abduldev.artbookapptestingguide.R
import com.abduldev.artbookapptestingguide.db.enities.Art
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ImageAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ImageAdapter.ImageAdapterViewHolder>() {
    inner class ImageAdapterViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view)

    private var onItemClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ImageAdapterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_row, parent, false)
        return ImageAdapterViewHolder(view)
    }

    private val callback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String, newItem: String
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String, newItem: String
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, callback)

     var images: List<String>
        get() = differ.currentList
        set(value) = differ.submitList(value)


     fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }


    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(
        holder: ImageAdapterViewHolder, position: Int
    ) {
        val imageView = holder.itemView
            .findViewById<ImageView>(R.id.searchedImage)
        val url = images[position]

        holder.itemView.apply {
            glide.load(url).into(imageView)
            setOnClickListener {
                onItemClickListener?.let {
                    it(url)
                }
            }
        }
    }
}