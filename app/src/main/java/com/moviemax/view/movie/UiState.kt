package com.moviemax.view.movie

import com.common.util.UiText

sealed class UiState {
    object None : UiState()
    object Loading : UiState()
    data class Success<T>(val data: T) : UiState()
    data class Error(val message: UiText) : UiState()


    fun <T> asSuccess() : UiState.Success<T>{
        return this as UiState.Success<T>
    }
    fun asError() : UiState.Error {
        return this as UiState.Error
    }
}