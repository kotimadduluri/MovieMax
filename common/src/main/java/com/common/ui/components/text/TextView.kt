package com.common.ui.components.text

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.common.R
import com.common.ui.components.icon.IconWithDrawable
import com.common.util.UiImage
import com.common.util.UiText

@Composable
fun TextView(
    text: UiText,
    icon: UiImage?=null,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    fontColor: Color = Color.Black,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.SemiBold,
) {
    Row {
        icon?.let {
            IconWithDrawable(
                icon = icon,
                tint = fontColor
                )
        }
        Text(
            text = text.asString(),
            modifier = modifier,
            color = fontColor,
            fontSize = fontSize,
            style = textStyle,
            fontWeight = fontWeight
        )
    }
}