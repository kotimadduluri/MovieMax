package com.moviemax.common

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

open class BaseViewModel<T>(state:T) : ViewModel() {
    fun uiState() : State<T> = uiState
    protected val uiState = mutableStateOf(state)
}