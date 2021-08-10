package com.example.unsplashimageapp.ui.fragments.homeFragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.unsplashimageapp.R
import com.example.unsplashimageapp.databinding.FragmentHomeBinding
import com.example.unsplashimageapp.ui.fragments.homeFragment.adapter.ImagesAdapter
import com.example.unsplashimageapp.ui.fragments.homeFragment.adapter.LoadingStateAdapter
import com.example.unsplashimageapp.utils.Constants.TAG
import com.example.unsplashimageapp.utils.ItemComparator
import com.example.unsplashimageapp.viewmodel.MainViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import timber.log.Timber
import java.util.*


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
                    Timber.d(query)
                    //subscribeAllImagesObserver()
                }
                else ->
                {
                    Timber.d(query)
                    viewModel.searchImages(query)
                    subscribeSearchObserver()
                }
            }
        } // chipGroup onCheckedListener closed



        return binding.root

    } // onCreateView closed


    private fun subscribeAllImagesObserver()
    {
        viewModel.allImages.observe(viewLifecycleOwner)
        {
            it.let ()
            {
                adapter.submitData(lifecycle,it)
            } // it closed
        } // observer closed
    } // subscribeAllImagesObserver closed


    private fun subscribeSearchObserver()
    {
        viewModel.queriedImages.observe(viewLifecycleOwner)
        {
            it?.let()
            {
                adapter.submitData(lifecycle,it)
            }
        }
    }

    private fun setupRecyclerView(recycler: RecyclerView)
    {
        recycler.setHasFixedSize(true)
        //recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        recycler.adapter = adapter
            .withLoadStateHeaderAndFooter(header = LoadingStateAdapter{adapter.retry()}
        ,footer = LoadingStateAdapter{adapter.retry()}
        )
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null

    } // OnDestroyView closed





} // HomeFragment closed