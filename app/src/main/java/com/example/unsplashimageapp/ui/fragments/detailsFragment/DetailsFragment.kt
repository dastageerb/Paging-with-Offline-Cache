package com.example.unsplashimageapp.ui.fragments.detailsFragment

import android.net.Network
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.unsplashimageapp.R
import com.example.unsplashimageapp.databinding.FragmentDetailsBinding
import com.example.unsplashimageapp.utils.Constants.TAG
import com.example.unsplashimageapp.utils.NetworkResource
import com.example.unsplashimageapp.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class DetailsFragment : Fragment()
{

    private var _binding:FragmentDetailsBinding?=null
    private val binding get() = _binding!!
    private val detailsViewModel:DetailsViewModel by viewModels()
    private val args by navArgs<DetailsFragmentArgs>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)


        detailsViewModel.getSinglePhoto(args.id)

        subscribeObserver()


        return binding.root

    }

    private fun subscribeObserver()
    {
        detailsViewModel.singlePhoto.observe(viewLifecycleOwner)
        {
            when(it)
            {
                is NetworkResource.Success ->
                {
                    Timber.tag(TAG).d("Success -> "+it.data)
                }
                is NetworkResource.Error ->
                {
                    Timber.tag(TAG).d("Error -> "+it.message)
                }
                is NetworkResource.Loading -> Timber.tag(TAG).d("Loading -> ")
            }

        } // observer closed
    } //  subscribeObserver closed

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }


} // DetailsFragment closed

