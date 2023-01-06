package com.example.pagingWithOfflineCache.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pagingWithOfflineCache.data.local.ImageDbEntity
import com.example.pagingWithOfflineCache.databinding.LayoutImageItemBinding

class ImagesAdapter : PagingDataAdapter<ImageDbEntity, ImagesAdapter.ViewHolder>(PlayersDiffCallback()) {

    inner class ViewHolder(private val binding:LayoutImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item:ImageDbEntity) {
            binding.layoutImageItemImageView.load(item.url)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutImageItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    private class PlayersDiffCallback : DiffUtil.ItemCallback<ImageDbEntity>() {
        override fun areItemsTheSame(oldItem: ImageDbEntity, newItem: ImageDbEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageDbEntity, newItem: ImageDbEntity): Boolean {
            return oldItem == newItem
        }
    }

}