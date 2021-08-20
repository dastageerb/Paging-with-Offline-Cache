package com.example.unsplashimageapp.ui.fragments.detailsFragment

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.unsplashimageapp.R
import com.example.unsplashimageapp.data.Entity.responses.PhotoResponse
import com.example.unsplashimageapp.databinding.FragmentDetailsBinding
import com.example.unsplashimageapp.ui.fragments.detailsFragment.downloadService.DownloadService
import com.example.unsplashimageapp.utils.Constants
import com.example.unsplashimageapp.utils.Constants.APP_PACKAGE_NAME
import com.example.unsplashimageapp.utils.Constants.TAG
import com.example.unsplashimageapp.utils.DownloadStatus
import com.example.unsplashimageapp.utils.ExtensionFunction.disabled
import com.example.unsplashimageapp.utils.ExtensionFunction.enabled
import com.example.unsplashimageapp.utils.ExtensionFunction.format
import com.example.unsplashimageapp.utils.ExtensionFunction.hide
import com.example.unsplashimageapp.utils.ExtensionFunction.load
import com.example.unsplashimageapp.utils.ExtensionFunction.show
import com.example.unsplashimageapp.utils.ExtensionFunction.showToast
import com.example.unsplashimageapp.utils.NetworkResource
import com.example.unsplashimageapp.utils.permissions.Permissions.hasPermission
import com.example.unsplashimageapp.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.util.*


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailsFragment : Fragment() , View.OnClickListener
{

    private var _binding:FragmentDetailsBinding?=null
    private val binding get() = _binding!!
    private val detailsViewModel:DetailsViewModel by viewModels()
    private val args by navArgs<DetailsFragmentArgs>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        loadViewFromSafeArgs()


        detailsViewModel.getPhotoDetails(args.photoDetails.photoId.toString())

        getPhotoDetailsResponse();

        binding.imageViewBack.setOnClickListener(this)
        binding.buttonDetailsFragDownload.setOnClickListener(this)

      //  getDownloadResponse()

        return binding.root
    } // onCreateView closed

    private fun loadViewFromSafeArgs()
    {
        // load views from safe args
        binding.apply ()
        {
            detailsLayout.hide()
            progressBarDetailsFrag.show()
            textViewUserName.text = args.photoDetails.username
            textViewDescription.text = args.photoDetails.description
            imageViewDetailsFrag.load(args.photoDetails.imageUrl,R.drawable.ic_baseline_place_holder_24)
            imageViewUser.load(args.photoDetails.userImageUrl,R.drawable.ic_baseline_person_24)
        } // apply closed

    } // loadViewsFromSafeArgs closed


    private fun getPhotoDetailsResponse()
    {

        detailsViewModel // declared before coroutine to avoid exception
        lifecycleScope.launch(Dispatchers.Main)
        {
            detailsViewModel.imageDetailsResponse.collect ()
            {
                when(it)
                {
                    is NetworkResource.Success ->
                    {
                        binding.progressBarDetailsFrag.hide()
                        loadViews(it.data!!)
                        Timber.tag(TAG).d("Success -> %s", it.data)
                    }
                    is NetworkResource.Error ->
                    {
                        binding.progressBarDetailsFrag.hide()
                        requireContext().showToast(it.message.toString())
                        Timber.tag(TAG).d("Error -> %s", it.message)
                    }
                    is NetworkResource.Loading -> binding.progressBarDetailsFrag.show()
                } // when closed

            } // collect closed
        } // lifecycle coroutine closed

    } // getPhotoDetailsResponse closed

    // load more Views from Api Response
    private fun loadViews(data: PhotoResponse)
    {
        // load views from response
        binding.apply()
        {
            textViewDownloadsCount.text = data.downloads?.format()
            textViewViewsCount.text = data.views?.format()
            textViewLocation.text =  data.location?.country ?: "unavailable"
            textViewCreationDate.text = data.createdAt?.substring(0,10)
            detailsLayout.show()
        } // apply closed

    } // loadViews closed


    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            R.id.buttonDetailsFragDownload ->
            {
                checkPermissionAndDownload();
            }
            R.id.imageViewBack ->
            {
                findNavController().navigate(R.id.action_detailsFragment_to_homeFragment)
            }
        } // when closed
    } // onClick closed



    private fun checkPermissionAndDownload()
    {
        when(hasPermission(requireContext()))
        {
            true -> startDownloading()
            else -> if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P)
            {
                mPermissionResult.launch(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.FOREGROUND_SERVICE))
            }else
            {
                mPermissionResult.launch(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            }
        } // when closed
    } // checkPermissionAndDownload


    private val mPermissionResult = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
    { permissions ->

        // Handle Permission granted/rejected
        permissions.entries.forEach()
        {

            val isGranted = it.value
            if (isGranted)
            {
                startDownloading()
            } else
            {
                requireContext().showToast("App needs Permissions to Continue")
                startActivity(Intent(ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", APP_PACKAGE_NAME, null)
               })
            } // else closed
        } /// forEach closed
    } // Request Permission closed

        private fun startDownloading()
        {
            // Start Download service
            Intent(context, DownloadService::class.java).apply ()
            {
                    putExtra(Constants.IMAGE_URL,args.photoDetails.downloadUrl)
                    requireContext().startService(this)
            } // intent closed

            getDownloadResponse()

        } // downloadImage closed




        private fun getDownloadResponse()
        {

            // response from download service
            lifecycleScope.launch()
            {
                DownloadService.downloadingStatus.collect ()
                {
                    when(it)
                    {
                        DownloadStatus.Success ->
                        {
                            requireContext().showToast("Image Downloaded")
                            binding.buttonDetailsFragDownload.enabled()
                            DownloadService.downloadingStatus.value = DownloadStatus.Null
                        } // Success closed
                        DownloadStatus.Error ->
                        {
                            requireContext().showToast("Downloading failed ")
                            binding.buttonDetailsFragDownload.enabled()
                        } // Error closed
                        DownloadStatus.Loading ->
                        {
                            requireContext().showToast("Downloading ...")
                            binding.buttonDetailsFragDownload.disabled()
                        } // Loading closed

                        else -> { /* do nothing */ }
                    } // when closed

                } // collect closed
            } //

        } // getImageDownloadResponse


        override fun onDestroyView()
        {
            super.onDestroyView()
            _binding = null
        } // onDestroy closed



} // DetailsFragment closed

