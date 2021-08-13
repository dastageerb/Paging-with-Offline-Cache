package com.example.unsplashimageapp.ui.fragments.homeFragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.util.*

/** @author
 * Dastageer
 * */

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


        /** There are two Api call in Home Fragment     */
        /* first gets the data on launch */

        binding.chipHomeFragAll.isChecked = true
        getAllImages()

        /** Second call is triggered when a diff chip is checked */
        /* chip text is used as query */
        binding.chipGroupHomeFrag.setOnCheckedChangeListener()
        {
                group,selectedId ->
                val query = getQueryFromChip(group,selectedId)
                getSearchedResult(query)
        }

        return binding.root

    } // onCreateView closed

    fun getQueryFromChip(group: ChipGroup?, selectedId: Int): String
    {
        val chip = group?.findViewById<Chip>(selectedId)
        return chip?.text.toString().toLowerCase(Locale.ROOT)
    }

    private fun getSearchedResult(query: String)
    {
       when(query == "all")
       {
           true ->{
               getAllImages() ; Timber.tag(TAG).d(query)
           }
           else -> { searchImages(query)  }
       } // when closed

    } // getSearchedResult

    private fun searchImages(query: String)
    {
        if(!requireContext().hasInternetConnection())
        {
            binding.textViewNoInternet.show()
        }else
        {
            binding.textViewNoInternet.hide()
            loadImagesIntoRecycler(query)
        } // else closed
    } // searchImages closed

    private fun loadImagesIntoRecycler(query: String)
    {
        binding.progressBarHomeFrag.show()
        Timber.tag(TAG).d(query)
        viewModel // to avoid observer on main thread Exception
        lifecycleScope.launch(Dispatchers.IO)
        {
            viewModel.getSearchedImages(query).collect()
            {

                withContext(Dispatchers.Main) // loadViews on main thread
                {
                    Timber.tag(TAG).d(""+it)
                    binding.progressBarHomeFrag.hide()
                    adapter.submitData(lifecycle,it)

                } // withContext closed
            } // getAllImages

        } // coroutine closed

    } // loadAllImagesIntoRecycler


    private fun getAllImages()
    {
        if(!requireContext().hasInternetConnection())
        {
            binding.textViewNoInternet.show()
        }else
        {
            binding.textViewNoInternet.hide()
            loadImagesIntoRecycler();
        } // else closed
    } // getAllImages closed

    private fun loadImagesIntoRecycler()
    {
        binding.progressBarHomeFrag.show()
        viewModel // to avoid observer on main thread Exception
        lifecycleScope.launch(Dispatchers.IO)
        {
            viewModel.getAllImages().collect()
            {
                withContext(Dispatchers.Main) // loadViews on main thread
                {
                    binding.progressBarHomeFrag.hide()
                    adapter.submitData(lifecycle,it)
                } // withContext closed
            } // getAllImages

        } // coroutine closed

    } // loadAllImagesIntoRecycler


    private fun setupRecyclerView(recycler: RecyclerView)
    {
        recycler.setHasFixedSize(true)
        recycler.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        recycler.adapter = adapter

//            .withLoadStateHeaderAndFooter(header = LoadingStateAdapter{adapter.retry()}
//        ,footer = LoadingStateAdapter{adapter.retry()}
//        )

    } // setupRecyclerView





    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null

    } // OnDestroyView closed





} // HomeFragment closed