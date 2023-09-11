package com.common.util

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

sealed class UiImage{
    data class DrawableResource(
        @DrawableRes val id: Int,
    ) : UiImage()

    @Composable
    fun asPainter(): Painter {
        return when (this) {
            is DrawableResource -> painterResource(id = id)
        }
    }
}