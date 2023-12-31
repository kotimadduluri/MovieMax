package com.common.ui.components.text

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
import com.common.util.UiImage
import com.common.util.UiText

@Preview(showBackground = true)
@Composable
fun TextViewPreview() {
    TextView(
        text = UiText.StringResource(R.string.noLabel),
        icon = UiImage.DrawableResource(R.drawable.ic_warning)
    )
}

@Composable
fun TitleTextView(
    modifier: Modifier = Modifier,
    text: UiText,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    fontColor: Color = Color.Black,
    fontSize: TextUnit = 18.sp,
) {
    Text(
        text = text.asString(),
        modifier = modifier,
        color = fontColor,
        fontSize = fontSize,
        style = textStyle,
        fontWeight = FontWeight.Bold,
    )
}

@Preview(showBackground = true)
@Composable
fun TitleTextViewPreview() {
    TitleTextView(text = UiText.StringResource(R.string.noLabel))
}