package com.moviemax.ui.components.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun DefaultNavigationIcon(onclick: () -> Unit) = IconButton(onClick = onclick) {
    Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = "Nav"
    )
}