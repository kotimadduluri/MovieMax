package com.moviemax.view.auth

import androidx.compose.runtime.Composable
import com.common.ui.components.button.ButtonWithProgressBar
import com.common.util.UiText
import com.moviemax.R

@Composable
fun RegistrationScreen(onEvent:()->Unit) {
    ButtonWithProgressBar(text = UiText.StringResource(R.string.registration)){
        onEvent()
    }
}