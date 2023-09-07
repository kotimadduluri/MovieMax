package com.moviemax.view.movie

sealed class UiState {
    object None : UiState()
    object Loading : UiState()
    data class Success<T>(val data: T) : UiState()
    data class Error(val message: String) : UiState()
}