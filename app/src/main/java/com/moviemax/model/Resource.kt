package com.moviemax.model


sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val status: STATUS
) {

    class Success<T>(data: T?) :
        Resource<T>(data = data, status = STATUS.SUCCESS)

    class Error<T>(
        data: T? = null,
        message: String? = null,
        error: Throwable? = null
    ) :
        Resource<T>(data, status = STATUS.ERROR, message = message)

    enum class STATUS {
        SUCCESS, ERROR
    }
}