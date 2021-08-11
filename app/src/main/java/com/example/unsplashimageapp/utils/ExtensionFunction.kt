package com.example.unsplashimageapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View

object ExtensionFunction
{
    public fun View.show()
    {
        this.visibility = View.VISIBLE
    }

    public fun View.hide()
    {
        this.visibility = View.GONE
    }

    // check internet connection
    fun Context.hasInternetConnection() : Boolean
    {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when
        {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)-> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        } // return When closed
    } // hasInternetConnection





}  // ExtensionFunctions closed