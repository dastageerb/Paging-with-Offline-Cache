package com.example.unsplashimageapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso
import retrofit2.http.Url
import java.net.URI
import java.net.URL
import java.util.*

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



    // extension function for Picasso

    fun ImageView.load(url: String?)
    {
        Picasso.get().load(url).into(this)
    }

    fun ImageView.load(url: String?, placeHolder: Int)
    {
        Picasso.get().load(url).placeholder(placeHolder).into(this)
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


    fun Context.showToast(msg: String)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    } // showToast closed


    var str = "http://google.com"
    var uri: URI = URI(str)
    var url: URL = uri.toURL()

    fun toUrl(url: String?) :URL =  URI(url).toURL()







}  // ExtensionFunctions closed