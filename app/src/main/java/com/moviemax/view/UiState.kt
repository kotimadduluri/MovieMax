package com.moviemax.view

import androidx.compose.runtime.Composable
import com.common.util.UiText

sealed class UiState {
    data object None : UiState()
    data object Loading : UiState()
    data class Success<T>(val data: T) : UiState()
    data class Error(val message: UiText) : UiState()
    data class MultipleError<T>(val errors: List<UiText?>) : UiState(){

        @Composable
        fun getErrorMessage():UiText{
            val stringBuilder = StringBuilder()
            errors.forEach {error->
                error?.let {
                    stringBuilder.append(it.asString())
                    stringBuilder.append("\n")
                }
            }
            return UiText.PlainString(stringBuilder.toString())
        }
    }


    fun <T> asSuccess() : Success<T> {
        return this as Success<T>
    }
    fun asError() : Error {
        return this as Error
    }
}