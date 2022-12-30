package com.example.unsplashimageapp.utils

import android.content.Context
import android.icu.number.NumberFormatter.with
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

open class ExtensionFunction
{
    public fun View.show() {
        this.visibility = View.VISIBLE
    }

    fun View.hide() {
        this.visibility = View.GONE
    }

    fun AppCompatButton.enabled() {
        this.isEnabled = true
    }

    fun AppCompatButton.disabled() {
        this.isEnabled = false
    }

    fun ImageView.load(url: String?, placeHolder: Int) {
        Picasso.get().load(url).placeholder(placeHolder).into(this)
    }

    fun Context.showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    } // showToast closed

}  // ExtensionFunctions closed