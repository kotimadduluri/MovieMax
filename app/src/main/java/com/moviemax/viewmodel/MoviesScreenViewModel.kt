package com.moviemax.viewmodel

import androidx.lifecycle.viewModelScope
import com.moviemax.common.BaseViewModel
import com.moviemax.model.Resource
import com.moviemax.model.movie.data.getMovies
import com.moviemax.model.movie.data.remote.model.MoviesResponse
import com.moviemax.model.movie.usecase.GetMoviesUseCase
import com.moviemax.view.movie.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MoviesScreenViewModel(
    private val moviesUseCase: GetMoviesUseCase,
) : BaseViewModel<UiState>(UiState.None) {

    private val currentPage = MutableStateFlow(1)

    init {
        getMovies(currentPage.value)
    }

    fun getMovies(page: Int = 1) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            val networkResponse = moviesUseCase(page)
            if (networkResponse is Resource.Success<*>) {
                val response = networkResponse as Resource.Success<MoviesResponse>
                response.data?.getMovies()?.let {
                    uiState.value = UiState.Success(it)
                } ?: { uiState.value = UiState.Error("No records found") }
            } else {
                uiState.value = UiState.Error(networkResponse.message ?: "Something went wrong")
            }
        }
    }
}