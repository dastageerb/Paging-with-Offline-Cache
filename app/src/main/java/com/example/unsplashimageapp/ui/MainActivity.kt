package com.example.unsplashimageapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.unsplashimageapp.BuildConfig
import com.example.unsplashimageapp.utils.Constants.TAG
import com.example.unsplashimageapp.R
import com.example.unsplashimageapp.data.api.UnSplashApi
import com.example.unsplashimageapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

@DelicateCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{
    private var _binding : ActivityMainBinding?=null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fragment_nav_host)


    } // onCreateView closed

    override fun onDestroy()
    {
        super.onDestroy()
        _binding = null
    }

    override fun onSupportNavigateUp(): Boolean
    {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


} // main closed


