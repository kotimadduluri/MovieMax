package com.common.ui.components.button

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.common.R
import com.common.ui.components.text.TextView
import com.common.util.UiImage
import com.common.util.UiText

@Composable
fun SimpleButton(
    text: UiText,
    icon:UiImage?=null,
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
        TextView(
            text = text,
            icon=icon,
        )
    }
}

@Composable
@Preview
fun SimpleButtonPreview() {
    SimpleButton(
        text = UiText.StringResource(R.string.default_label_button)
    )
}