package com.common.ui.components.button

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
import com.common.R
import com.common.ui.components.text.TextView
import com.common.ui.theme.spacing
import com.common.util.UiImage
import com.common.util.UiText

@Composable
fun ButtonWithProgressBar(
    text: UiText,
    icon: UiImage?=null,
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
                modifier = Modifier.size(MaterialTheme.spacing.medium),
            )
        } else TextView(
            text = text,
            icon=icon,
            textStyle = MaterialTheme.typography.bodyMedium,
            fontColor = contentColor
        )
    }
}

@Composable
@Preview
fun ButtonWithProgressBarPreview() {
    ButtonWithProgressBar(
        text = UiText.StringResource(R.string.default_label_button),
        isLoading = false,
    )
}