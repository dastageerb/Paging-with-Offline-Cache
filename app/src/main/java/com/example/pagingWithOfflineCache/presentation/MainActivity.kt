package com.example.pagingWithOfflineCache.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingWithOfflineCache.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel:MainViewModel by viewModels()
    private lateinit var imagesAdapter: ImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView(binding.activityMainRecyclerView)
        subscribeToImages()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        imagesAdapter = ImagesAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = imagesAdapter
    }

    private fun subscribeToImages() {
        lifecycleScope.launch(Dispatchers.Main) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getAllImages.collect { pagingData ->
                    imagesAdapter.submitData(pagingData)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


