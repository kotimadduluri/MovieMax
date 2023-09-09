package com.moviemax.model


sealed class Resource(
    val message: String? = null,
    val status: STATUS
) {

    class Success<T>(val data: T?) :
        Resource(status = STATUS.SUCCESS)

    class Error(
        message: String? = null,
        error: Throwable? = null
    ) :
        Resource(status = STATUS.ERROR, message = message)

    enum class STATUS {
        SUCCESS, ERROR
    }
}