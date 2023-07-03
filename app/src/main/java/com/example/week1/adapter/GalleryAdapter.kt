package com.example.week1.adapter

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.net.Uri
import android.view.View
import com.example.week1.R
import com.example.week1.databinding.ItemImageBinding

class GalleryAdapter(private val images: List<String>) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryAdapter.ViewHolder, position: Int) {
        val imagePath = images[position]

        holder.image.setImageURI(Uri.parse(imagePath))
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.imageView
    }
}
