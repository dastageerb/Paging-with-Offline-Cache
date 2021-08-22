package com.example.unsplashimageapp.ui.fragments.homeFragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.unsplashimageapp.databinding.FragmentHomeBinding
import com.example.unsplashimageapp.ui.fragments.homeFragment.adapter.ImagesAdapter
import com.example.unsplashimageapp.utils.Constants.TAG
import com.example.unsplashimageapp.utils.ExtensionFunction.hide
import com.example.unsplashimageapp.utils.ExtensionFunction.show
import com.example.unsplashimageapp.utils.NetworkResource
import com.example.unsplashimageapp.utils.diffutil.ItemComparator
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

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment()
{

    private var _binding:FragmentHomeBinding? =null
    private val binding get() = _binding!!
    private val viewModel:MainViewModel by viewModels()
    private val  adapter = ImagesAdapter(ItemComparator)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        setupRecyclerView(binding.recyclerViewFragmentHome)

        /** There are two Api call in Home Fragment     */
        /* first gets the data when app  launched */
        binding.chipHomeFragAll.isChecked = true
        viewModel.requestPhotos()



        /** Second call is triggered when a diff chip is checked */
        /* chip text is used as query */
        binding.chipGroupHomeFrag.setOnCheckedChangeListener()
        {
                group,selectedId ->
                val query = getQueryFromChip(group,selectedId)
                makeASearchRequest(query)
        }


        //
        subscribeResponseFlow()

        return binding.root

    } // onCreateView closed



    private fun subscribeResponseFlow()
    {

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main)
        {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.getResponse.collect()
                {
                    when(it)
                    {
                        is NetworkResource.Loading ->
                        {
                            binding.progressBarHomeFrag.show()
                            Timber.tag(TAG).d("Data in Home Frag Loading")
                        }
                        is NetworkResource.Error ->
                        {
                            binding.progressBarHomeFrag.hide()
                            Timber.tag(TAG).d("Data in Home Frag Error")
                        }
                        is NetworkResource.Success ->
                        {
                            binding.progressBarHomeFrag.hide()
                           adapter.submitData(lifecycle,it.data!!)
                            Timber.tag(TAG).d("Data in Home Frag Success")
                        }
                        else -> {
                            Timber.tag(TAG).d("Data in Home Frag Else")}
                    } // when closed
                } // collect closed
            } // repeatOnLifeCycle closed
        }  // viewLifecycleOwner closed

    } // subscribeResponseFlow closed

    private fun getQueryFromChip(group: ChipGroup?, selectedId: Int): String
    {
        val chip = group?.findViewById<Chip>(selectedId)
        return chip?.text.toString().toLowerCase(Locale.ROOT)
    }

    private fun makeASearchRequest(query: String)
    {

       when(query == "all")
       {
           true -> viewModel.requestPhotos()
           else ->  viewModel.requestPhotos(query)
       } // when closed
    } // getSearchedResult



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