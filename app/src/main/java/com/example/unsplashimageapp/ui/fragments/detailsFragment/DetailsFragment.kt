package com.example.unsplashimageapp.ui.fragments.detailsFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.unsplashimageapp.R
import com.example.unsplashimageapp.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment()
{

    private var _binding:FragmentDetailsBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)

        return binding.root

    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }


} // DetailsFragment closed

