package com.moviemax.common

import com.moviemax.model.Resource
import com.moviemax.view.movie.UiState

fun <T> Resource.castAsSuccess() : Resource.Success<T>{
    return this as Resource.Success<T>
}

fun Resource.castAsError(): Resource.Error {
    return this as Resource.Error
}

fun <T> UiState.castAsSuccess() : UiState.Success<T>{
    return this as UiState.Success<T>
}

fun  UiState.castAsError() : UiState.Error{
    return this as UiState.Error
}