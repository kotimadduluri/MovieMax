package com.common.ui.components.actionbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import com.common.util.UiText

@Composable
fun DefaultNavigationIcon(
    contentDescription : UiText = UiText.PlainString("Nav"),
    onclick: () -> Unit
) = IconButton(onClick = onclick) {
    Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = contentDescription.asString()
    )
}