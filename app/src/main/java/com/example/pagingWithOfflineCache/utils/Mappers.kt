package com.example.pagingWithOfflineCache.utils

import com.example.pagingWithOfflineCache.data.Image
import com.example.pagingWithOfflineCache.data.local.ImageDbEntity

object Mappers {


    fun mapNetWorkEntityIntoDatabaseEntity(body: List<Image>): List<ImageDbEntity> {
        val list = mutableListOf<ImageDbEntity>()
        body.forEach{ image ->
            list.add(ImageDbEntity(image.id!!,image.urls?.thumb!!))
        }
        return list
    }

}