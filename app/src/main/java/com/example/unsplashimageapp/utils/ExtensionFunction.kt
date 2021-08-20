package com.example.unsplashimageapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

object ExtensionFunction
{

    public fun View.show()
    {
        this.visibility = View.VISIBLE
    }

     fun View.hide()
    {
        this.visibility = View.GONE
    }

    fun AppCompatButton.enabled()
    {
        this.isEnabled = true
    }


    fun AppCompatButton.disabled()
    {
        this.isEnabled = false
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

    fun Int.format() : String?
    =  NumberFormat.getNumberInstance(Locale.US).format(this);




}  // ExtensionFunctions closed