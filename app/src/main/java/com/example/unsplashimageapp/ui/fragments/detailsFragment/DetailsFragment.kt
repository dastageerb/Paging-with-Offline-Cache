package com.example.unsplashimageapp.ui.fragments.detailsFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.unsplashimageapp.R
import com.example.unsplashimageapp.data.Entity.responses.PhotoResponse
import com.example.unsplashimageapp.databinding.FragmentDetailsBinding
import com.example.unsplashimageapp.utils.Constants.TAG
import com.example.unsplashimageapp.utils.ExtensionFunction.hide
import com.example.unsplashimageapp.utils.ExtensionFunction.load
import com.example.unsplashimageapp.utils.ExtensionFunction.show
import com.example.unsplashimageapp.utils.NetworkResource
import com.example.unsplashimageapp.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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



   //     detailsViewModel.getSinglePhoto(args.id)


        detailsViewModel // declared before coroutine to avoid exception
        lifecycleScope.launch(Dispatchers.IO)
        {
            detailsViewModel.getSinglePhoto(args.id).collect ()
            {
                withContext(Dispatchers.Main)
                {
                    when(it)
                    {
                        is NetworkResource.Success ->
                        {
                            binding.progressBarDetailsFrag.hide()
                            loadViews(it.data!!)
                            Timber.tag(TAG).d("Success -> "+it.data)
                        }
                        is NetworkResource.Error ->
                        {
                            Timber.tag(TAG).d("Error -> "+it.message)
                        }
                        is NetworkResource.Loading -> binding.progressBarDetailsFrag.show()
                    } // when closed
                } // with context coroutine closed
            } // collect closed

        } // lifecycle coroutine closed


        return binding.root
    } // onCreateView closed


    private  fun loadViews(data: PhotoResponse)
    {
        binding.apply ()
        {
            binding.apply ()
            {
                imageViewUser.load(data.user?.profileImage?.medium)
                textViewDetailsFragUserName.text = data.user?.name
                imageViewDetailsFrag.load(data.urls?.regular,R.drawable.ic_baseline_place_holder_24)
                texViewDetailsFragViewsCount.text = data.views.toString()
                texViewDetailsFragDownloadsCount.text = data.downloads.toString()
                textViewDetailsFragLocation.text = data.location?.country
                detailsLayout.show()
            }
        }

    } // setupDetailsLayout




    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null

    } // onDestroy closed


} // DetailsFragment closed

