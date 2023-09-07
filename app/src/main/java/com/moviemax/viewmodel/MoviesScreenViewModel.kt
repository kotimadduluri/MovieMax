package com.moviemax.viewmodel

import androidx.lifecycle.viewModelScope
import com.moviemax.common.BaseViewModel
import com.moviemax.model.Resource
import com.moviemax.model.movie.data.getMovies
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
            val response = moviesUseCase(page)
            if (response is Resource.Success) {
                response.data?.getMovies()?.let {
                    uiState.value = UiState.Success(it)
                } ?: { uiState.value = UiState.Error("No records found") }
            } else {
                uiState.value = UiState.Error(response.message ?: "Something went wrong")
            }
        }
    }
}