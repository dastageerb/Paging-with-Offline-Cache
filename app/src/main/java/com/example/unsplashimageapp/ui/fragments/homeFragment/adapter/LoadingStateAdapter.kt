package com.example.unsplashimageapp.ui.fragments.homeFragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashimageapp.databinding.LayoutLoadStateAdapterBinding

class LoadingStateAdapter(val retry:()->Unit) : LoadStateAdapter<LoadingStateAdapter.ViewHolder>()
{

    inner class ViewHolder(binding: LayoutLoadStateAdapterBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder
    {
        val view = LayoutLoadStateAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState)
    {
        /*  Loading: Data is loading.
            NotLoading: No loading of data happening, and no error.
            Error: Fetching data ends with an error. */

        LayoutLoadStateAdapterBinding.bind(holder.itemView).apply()
        {

            when(loadState)
            {
                is LoadState.Loading ->
                {
                    progressBar.isVisible
                    !buttonRetry.isVisible
                }
                is LoadState.NotLoading ->
                {
                    !progressBar.isVisible
                    !buttonRetry.isVisible
                }
                is LoadState.Error ->
                {
                    buttonRetry.isVisible
                    !progressBar.isVisible
                }
            } // when closed

            buttonRetry.setOnClickListener()
            {
                retry.invoke()
            }
        }

    }



}