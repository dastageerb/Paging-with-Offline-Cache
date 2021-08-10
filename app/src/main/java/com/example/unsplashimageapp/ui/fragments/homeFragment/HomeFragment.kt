package com.example.unsplashimageapp.ui.fragments.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.unsplashimageapp.R
import com.example.unsplashimageapp.databinding.FragmentHomeBinding
import com.example.unsplashimageapp.ui.fragments.homeFragment.adapter.ImagesAdapter
import com.example.unsplashimageapp.ui.fragments.homeFragment.adapter.LoadingStateAdapter
import com.example.unsplashimageapp.utils.Constants.TAG
import com.example.unsplashimageapp.utils.ItemComparator
import com.example.unsplashimageapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class HomeFragment : Fragment()
{

    var _binding:FragmentHomeBinding? =null
    private val binding get() = _binding!!
    private val viewModel:MainViewModel by viewModels()
    private val adapter:ImagesAdapter by lazy { ImagesAdapter(ItemComparator) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        setupRecyclerView(binding.recyclerViewFragmentHome)
        viewModel.getImages.observe(viewLifecycleOwner)
        {
            it.let ()
            {
                Timber.tag(TAG).d("itemList : "+it)
                adapter.submitData(lifecycle,it)
            }
        }


        return binding.root

    } // onCreateView closed

    private fun setupRecyclerView(recycler: RecyclerView)
    {
        recycler.setHasFixedSize(true)
        recycler.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recycler.adapter = adapter.withLoadStateHeaderAndFooter(header = LoadingStateAdapter{adapter.retry()}
        ,footer = LoadingStateAdapter{adapter.retry()}
        )
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null

    } // OnDestroyView closed

}