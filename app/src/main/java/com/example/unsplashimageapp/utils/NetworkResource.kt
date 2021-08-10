package com.example.unsplashimageapp.utils

sealed class NetworkResource<T>
    (val data:T? = null ,val message:String?=null)
{
        class Loading<T>()  : NetworkResource<T>()
        class Success<T>(data: T?) : NetworkResource<T>(data)
        class Error<T>(message: String?,data: T?=null) : NetworkResource<T>(data,message)

}