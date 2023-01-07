package com.example.pagingWithOfflineCache.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingWithOfflineCache.databinding.LayoutLoadingStateBinding

class ImageLoadingStateAdapter() : LoadStateAdapter<ImageLoadingStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(val binding: LayoutLoadingStateBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        when (loadState) {
            is LoadState.Loading -> {
                holder.binding.layoutLoadingStateProgressbar.visibility = View.VISIBLE
            }
            else -> {
                holder.binding.layoutLoadingStateProgressbar.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            LayoutLoadingStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}