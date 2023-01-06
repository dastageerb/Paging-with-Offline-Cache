package com.example.pagingWithOfflineCache.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
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

    fun Context.showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    } // showToast closed

}  // ExtensionFunctions closed