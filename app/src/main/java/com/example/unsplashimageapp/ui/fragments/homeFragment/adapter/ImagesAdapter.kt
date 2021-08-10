package com.example.unsplashimageapp.ui.fragments.homeFragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.example.unsplashimageapp.data.Entity.UnSplashResponseItem
import com.example.unsplashimageapp.databinding.LayoutRecylerImageItemsBinding
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
            Picasso.get().load(item?.urls?.small).into(imageViewRecyclerImageItems)
        }
    }


}