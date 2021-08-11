package com.example.unsplashimageapp.ui.fragments.homeFragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.unsplashimageapp.databinding.FragmentHomeBinding
import com.example.unsplashimageapp.ui.fragments.homeFragment.adapter.ImagesAdapter
import com.example.unsplashimageapp.utils.Constants.TAG
import com.example.unsplashimageapp.utils.ExtensionFunction
import com.example.unsplashimageapp.utils.ExtensionFunction.hasInternetConnection
import com.example.unsplashimageapp.utils.ExtensionFunction.hide
import com.example.unsplashimageapp.utils.ExtensionFunction.show
import com.example.unsplashimageapp.utils.ItemComparator
import com.example.unsplashimageapp.viewmodel.MainViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class HomeFragment : Fragment()
{

    var _binding:FragmentHomeBinding? =null
    private val binding get() = _binding!!
    private val viewModel:MainViewModel by viewModels()
    private val adapter:ImagesAdapter = ImagesAdapter(ItemComparator)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        setupRecyclerView(binding.recyclerViewFragmentHome)

        subscribeAllImagesObserver()

        binding.chipGroupHomeFrag.setOnCheckedChangeListener()
        {
            group,selectedId ->

            val chip = group.findViewById<Chip>(selectedId)
            val query = chip.text.toString().toLowerCase()
            when(query)
            {
                "all" ->
                {
                    // if category/chip - all is checked get all photos
                    subscribeAllImagesObserver()
                }
                else ->
                {
                    viewModel.searchImages(query)
                    subscribeSearchObserver()
                }
            }
        } // chipGroup onCheckedListener closed



        return binding.root

    } // onCreateView closed


    private fun setupRecyclerView(recycler: RecyclerView)
    {
        recycler.setHasFixedSize(true)
        recycler.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        recycler.adapter = adapter
//            .withLoadStateHeaderAndFooter(header = LoadingStateAdapter{adapter.retry()}
//        ,footer = LoadingStateAdapter{adapter.retry()}
//        )
    }





    private fun subscribeAllImagesObserver()
    {

        when(requireContext().hasInternetConnection())
        {
            true ->
            {
                binding.textViewNoInternet.hide() // hide no internet icon

                viewModel.allImages.observe(viewLifecycleOwner)
                {
                    it.let ()
                    {
                        adapter.submitData(lifecycle,it)
                    } // it closed
                } // observer closed

            } // true closed

            false ->
            {
                binding.textViewNoInternet.show()
            } // false closed

        } // when closed


    } // subscribeAllImagesObserver closed

    // checks internet before observing
    private fun subscribeSearchObserver()
    {
        when(requireContext().hasInternetConnection())
        {
            true ->
            {
                binding.textViewNoInternet.hide() // hide no internet icon

                viewModel.queriedImages.observe(viewLifecycleOwner)
                {
                    it?.let()
                    {
                        adapter.submitData(lifecycle,it)
                    }
                }

            } // true closed

            false ->
            {
                binding.textViewNoInternet.show()
            } // false closed

        } // when closed
    } // subscribeSearchObserver closed


    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null

    } // OnDestroyView closed





} // HomeFragment closed