package com.moviemax.viewmodel

import androidx.lifecycle.viewModelScope
import com.moviemax.common.BaseViewModel
import com.moviemax.model.Resource
import com.moviemax.model.movie.data.remote.model.MovieDetailsResponse
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
            val networkResponse = movieDetailsUseCase(movieId)
            if (networkResponse is Resource.Success<*>) {
                val response = networkResponse as Resource.Success<MovieDetailsResponse>
                response.data?.let {
                    uiState.value = it.tvShow?.toMovies()?.let { movie -> UiState.Success(movie) }
                        ?: UiState.Error("Movie details not found")
                } ?: { uiState.value = UiState.Error("Movie details not found") }
            } else {
                uiState.value = UiState.Error(networkResponse.message ?: "Something went wrong")
            }
        }
    }
}