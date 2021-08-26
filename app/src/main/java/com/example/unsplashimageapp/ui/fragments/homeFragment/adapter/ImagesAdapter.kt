package com.example.unsplashimageapp.ui.fragments.homeFragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashimageapp.R
import com.example.unsplashimageapp.data.Entity.responses.UnSplashResponseItem
import com.example.unsplashimageapp.data.Entity.safeArgsObject.ImageDetails
import com.example.unsplashimageapp.databinding.LayoutRecylerImageItemsBinding
import com.example.unsplashimageapp.ui.fragments.homeFragment.HomeFragmentDirections
import com.example.unsplashimageapp.utils.ExtensionFunction.hide
import com.example.unsplashimageapp.utils.ExtensionFunction.load
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.Exception
import javax.inject.Inject

class ImagesAdapter (diffUtil: DiffUtil.ItemCallback<UnSplashResponseItem>)
    : PagingDataAdapter<UnSplashResponseItem,ImagesAdapter.ViewHolder>(diffUtil)
{

    var progressbar:ProgressBar? = null

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
//            imageViewRecyclerImageItems.load(item?.urls?.small)

            Picasso.get().load(item?.urls?.small)
                .into(imageViewRecyclerImageItems, object : Callback
                {
                    override fun onSuccess()
                    {
                        progressbar?.hide()
                    }
                    override fun onError(e: Exception?)
                    {
                        progressbar?.hide()
                    }
                })


        }

        holder.itemView.setOnClickListener()
        {
            val imageDetails = ImageDetails(
                item?.id,
                item?.user?.username,
                item?.user?.profileImage?.medium,
                item?.urls?.regular,
                item?.links?.download,
                item?.altDescription
            )
            val action  = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(imageDetails)
            holder.itemView.findNavController().navigate(action)
        } // itemView click listener closed


    } // onBindViewHolder closed


}