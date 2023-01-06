package com.example.pagingWithOfflineCache.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingWithOfflineCache.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    val viewModel:MainViewModel by viewModels()
    lateinit var imagesAdapter: ImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView(binding.activityMainRecyclerView)
        subscribeToImages()

    } // onCreateView closed

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        imagesAdapter = ImagesAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = imagesAdapter
    }

    private fun subscribeToImages() {
        viewModel.getAllImages.observe(this) { pagingData ->
            lifecycleScope.launch {
                Log.d("1234", "subscribeToImages: "+pagingData.toString())
                imagesAdapter.submitData(pagingData)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
} // main closed


