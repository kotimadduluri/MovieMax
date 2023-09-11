package com.moviemax.view.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.common.ui.components.button.ButtonWithProgressBar
import com.common.util.UiText
import com.moviemax.R

@Composable
fun LoginScreen(onEvent:()->Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        ButtonWithProgressBar(
            text = UiText.StringResource(R.string.login),
            modifier = Modifier.align(Alignment.Center)
        ){
            onEvent()
        }
    }
}