package com.example.unsplashimageapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.unsplashimageapp.data.Entity.responses.UnSplashResponseItem

object ItemComparator : DiffUtil.ItemCallback<UnSplashResponseItem>() {
  override fun areItemsTheSame(oldItem: UnSplashResponseItem, newItem: UnSplashResponseItem): Boolean {
    // Id is unique.
    return oldItem.id == newItem.id
  }

  override fun areContentsTheSame(oldItem: UnSplashResponseItem, newItem: UnSplashResponseItem): Boolean {
    return oldItem == newItem
  }
}