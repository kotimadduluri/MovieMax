package com.moviemax.ui.components.button

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.moviemax.ui.components.text.TextView

@Composable
fun SimpleButton(
    text: String,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    onButtClicked: () -> Unit = {}
) {
    Button(
        onClick = onButtClicked,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        TextView(text = text)
    }
}

@Composable
@Preview
fun SimpleButtonPreview() {
    SimpleButton(text = "SimpleButton")
}