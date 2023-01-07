package com.example.pagingWithOfflineCache.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pagingWithOfflineCache.R
import com.example.pagingWithOfflineCache.domain.ImageEntity
import com.example.pagingWithOfflineCache.databinding.LayoutImageItemBinding

class ImagesAdapter : PagingDataAdapter<ImageEntity, ImagesAdapter.ViewHolder>(PlayersDiffCallback()) {

    inner class ViewHolder(private val binding:LayoutImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ImageEntity) {
            binding.layoutImageItemImageView.load(item.imageUrl) {
                placeholder(R.drawable.plae_holder_layout)
            }
            binding.layoutImageItemImageViewUserProfile.load(item.userProfileImage)
            binding.layoutImageItemTextViewUserName.text = item.userName
            binding.layoutImageItemTextViewLocation.text = item.location
            binding.layoutImageItemTextViewLikes.text = "${item.likes} likes"
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

    private class PlayersDiffCallback : DiffUtil.ItemCallback<ImageEntity>() {
        override fun areItemsTheSame(oldItem: ImageEntity, newItem: ImageEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageEntity, newItem: ImageEntity): Boolean {
            return oldItem == newItem
        }
    }

}