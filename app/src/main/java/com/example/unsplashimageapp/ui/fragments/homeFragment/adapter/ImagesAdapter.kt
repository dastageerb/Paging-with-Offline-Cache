package com.example.unsplashimageapp.ui.fragments.homeFragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashimageapp.R
import com.example.unsplashimageapp.data.Entity.responses.UnSplashResponseItem
import com.example.unsplashimageapp.databinding.LayoutRecylerImageItemsBinding
import com.example.unsplashimageapp.ui.fragments.homeFragment.HomeFragmentDirections
import com.example.unsplashimageapp.utils.ExtensionFunction.load
import com.squareup.picasso.Picasso

class ImagesAdapter(diffUtil: DiffUtil.ItemCallback<UnSplashResponseItem>)
    : PagingDataAdapter<UnSplashResponseItem,ImagesAdapter.ViewHolder>(diffUtil)
{

    inner  class  ViewHolder(binding: LayoutRecylerImageItemsBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutRecylerImageItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val item = getItem(position)
        LayoutRecylerImageItemsBinding.bind(holder.itemView).apply ()
        {

            imageViewRecyclerImageItems.load(item?.urls?.small)
        }

        holder.itemView.setOnClickListener()
        {
            val action  = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item?.id.toString())
            holder.itemView.findNavController().navigate(action)
        }


    } // onBindViewHolder closed


}