package com.example.unsplashimageapp.data.Entity.safeArgsObject

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/** This data class is used to transfer object with safeArgs*/

@Parcelize
data class ImageDetails(
    val photoId:String?,
    val username:String?,
    val userImageUrl:String?,
    val imageUrl:String?,
    val downloadUrl:String?,
    val description:String?
    ) :  Parcelable
