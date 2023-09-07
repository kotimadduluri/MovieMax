package com.moviemax.viewmodel

import androidx.lifecycle.viewModelScope
import com.moviemax.common.BaseViewModel
import com.moviemax.model.Resource
import com.moviemax.model.movie.data.toMovies
import com.moviemax.model.movie.usecase.GetMovieDetailsUseCase
import com.moviemax.view.movie.UiState
import kotlinx.coroutines.launch

class MovieDetailsScreenViewModel(
    private val movieDetailsUseCase: GetMovieDetailsUseCase
) : BaseViewModel<UiState>(UiState.None) {

    fun getMovieDetails(movieId: Int) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            val response = movieDetailsUseCase(movieId)
            if (response is Resource.Success) {
                response.data?.let {
                    uiState.value = it.tvShow?.toMovies()?.let { movie -> UiState.Success(movie) }
                        ?: UiState.Error("Movie details not found")
                } ?: { uiState.value = UiState.Error("Movie details not found") }
            } else {
                uiState.value = UiState.Error(response.message ?: "Something went wrong")
            }
        }
    }
}