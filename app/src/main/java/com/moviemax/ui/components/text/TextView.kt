package com.moviemax.ui.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun TextView(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    fontColor: Color = Color.Black,
    fontSize: TextUnit = 16.sp
) {
    Text(
        text = text,
        modifier = modifier,
        color = fontColor,
        fontSize = fontSize,
        style = textStyle
    )
}

@Preview(showBackground = true)
@Composable
fun TextViewPreview() {
    TextView(text = "TextViewPreview")
}