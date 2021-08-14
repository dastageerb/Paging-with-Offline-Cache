package com.example.unsplashimageapp.ui.fragments.detailsFragment

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.unsplashimageapp.R
import com.example.unsplashimageapp.data.Entity.responses.PhotoResponse
import com.example.unsplashimageapp.databinding.FragmentDetailsBinding
import com.example.unsplashimageapp.utils.Constants.APP_PACKAGE_NAME
import com.example.unsplashimageapp.utils.Constants.TAG
import com.example.unsplashimageapp.utils.ExtensionFunction.hide
import com.example.unsplashimageapp.utils.ExtensionFunction.load
import com.example.unsplashimageapp.utils.ExtensionFunction.show
import com.example.unsplashimageapp.utils.ExtensionFunction.showToast
import com.example.unsplashimageapp.utils.NetworkResource
import com.example.unsplashimageapp.utils.Permissions.hasPermission
import com.example.unsplashimageapp.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.*


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailsFragment : Fragment()
{

    private var _binding:FragmentDetailsBinding?=null
    private val binding get() = _binding!!
    private val detailsViewModel:DetailsViewModel by viewModels()
    private val args by navArgs<DetailsFragmentArgs>()

    private lateinit var imageUrl:String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)



        detailsViewModel // declared before coroutine to avoid exception
        lifecycleScope.launch(Dispatchers.IO)
        {
            detailsViewModel.getPhotoDetails(args.id).collect ()
            {
                withContext(Dispatchers.Main)
                {
                    when(it)
                    {
                        is NetworkResource.Success ->
                        {
                            binding.progressBarDetailsFrag.hide()
                            loadViews(it.data!!)
                            Timber.tag(TAG).d("Success -> " + it.data)
                        }
                        is NetworkResource.Error ->
                        {
                            binding.progressBarDetailsFrag.hide()
                            requireContext().showToast(it.message.toString())
                            Timber.tag(TAG).d("Error -> " + it.message)
                        }
                        is NetworkResource.Loading -> binding.progressBarDetailsFrag.show()
                    } // when closed
                } // with context coroutine closed
            } // collect closed

        } // lifecycle coroutine closed


        binding.buttonDetailsFragDownload.setOnClickListener ()
        {
            when(hasPermission(requireContext()))
            {
                true -> downloadImage();
                else -> mPermissionResult.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            } // when closed

        } // onClick listener closed

        return binding.root
    } // onCreateView closed




    private val mPermissionResult = registerForActivityResult(RequestPermission())
    { result ->
        when(result)
        {
            true ->
            {
                downloadImage()
            }
            else ->
            {
                requireContext().showToast("App needs Permissions to Continue")
                startActivity(Intent(ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", APP_PACKAGE_NAME, null)
                })
            }
        } // when closed
    } // Request Permission closed



    private fun downloadImage()
    {
        //detailsViewModel.downloadPhoto(imageUrl)
        detailsViewModel

        lifecycleScope.launch(Dispatchers.IO)
        {

            detailsViewModel.download(imageUrl)
        }

    } //


    private  fun loadViews(data: PhotoResponse)
    {
        binding.apply ()
        {
            imageViewUser.load(data.user?.profileImage?.medium)
            textViewDetailsFragUserName.text = data.user?.name
            imageViewDetailsFrag.load(data.urls?.regular, R.drawable.ic_baseline_place_holder_24)
            texViewDetailsFragViewsCount.text = data.views.toString()
            texViewDetailsFragDownloadsCount.text = data.downloads.toString()
            textViewDetailsFragLocation.text = data.location?.country
            detailsLayout.show()

            imageUrl = data.links?.download.toString()
        }

    } // loadViews closed






    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null

    } // onDestroy closed


} // DetailsFragment closed

