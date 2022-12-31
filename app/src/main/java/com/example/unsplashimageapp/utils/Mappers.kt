package com.example.unsplashimageapp.utils

import com.example.unsplashimageapp.data.Image
import com.example.unsplashimageapp.data.local.ImageDbEntity

object Mappers {


    fun mapNetWorkEntityIntoDatabaseEntity(body: List<Image>): List<ImageDbEntity> {
        val list = mutableListOf<ImageDbEntity>()
        body.forEach{ image ->
            list.add(ImageDbEntity(image.id!!,image.urls?.thumb!!))
        }
        return list
    }

}