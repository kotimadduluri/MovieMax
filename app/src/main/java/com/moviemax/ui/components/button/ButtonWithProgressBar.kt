package com.moviemax.ui.components.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moviemax.ui.components.text.TextView

@Composable
fun ButtonWithProgressBar(
    text: String,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier.fillMaxWidth(),
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    onButtClicked: () -> Unit = {}
) {
    Button(
        onClick = {
            if (!isLoading) {
                onButtClicked()
            }
        },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isLoading) containerColor.copy(alpha = 0.5f) else containerColor,
            contentColor = contentColor
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp),
            )
        } else TextView(
            text = text,
            fontColor = contentColor,
            textStyle = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
@Preview
fun ButtonWithProgressBarPreview() {
    ButtonWithProgressBar(
        text = "Button",
        isLoading = false,
    )
}