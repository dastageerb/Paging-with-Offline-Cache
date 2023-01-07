package com.example.pagingWithOfflineCache.utils

import com.example.pagingWithOfflineCache.data.Image
import com.example.pagingWithOfflineCache.data.local.ImageDbEntity

object Mappers {


    fun mapNetWorkEntityIntoDatabaseEntity(body: List<Image>): List<ImageDbEntity> {
        val list = mutableListOf<ImageDbEntity>()
        body.forEach{ image ->
            list.add(
                ImageDbEntity(
                    id = image.id,
                    imageUrl = image.urls.imageUrl,
                    likes = image.likes,
                    location = image.user.location,
                    userName = image.user.username,
                    userProfileImage = image.user.profileImage.url
                )

            )
        }
        return list
    }

}