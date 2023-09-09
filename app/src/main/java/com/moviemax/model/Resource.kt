package com.moviemax.model

import com.moviemax.R
import com.moviemax.UiText


sealed class Resource(
    val message: UiText
) {

    class Success<T>(val data: T?) :
        Resource(message = UiText.StringResource(R.string.success))

    class Error(
        message : UiText = UiText.StringResource(R.string.error),
        error: Throwable? = null
    ) :
        Resource(message = message)
}

fun <T> Resource.asSuccess() : Resource.Success<T>{
    return this as Resource.Success<T>
}

fun Resource.asError(): Resource.Error {
    return this as Resource.Error
}