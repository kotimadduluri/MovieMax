package com.common.ui.components.state

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.common.ui.components.button.SimpleButton
import com.common.ui.components.text.TextView
import com.common.ui.theme.spacing

@Composable
fun ActionStateView(
    isActionRequired: Boolean = false,
    action: ActionState,
    modifier: Modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
    block: () -> Unit = {}
) {
    Column(
        modifier = modifier.testTag("ActionStateView"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = action.icon.asPainter(), // Replace with your error image resource
            contentDescription = action.title.asString(),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.size(MaterialTheme.spacing.small))

        TextView(
            text = action.message,
            fontSize = 20.sp,
            fontColor = MaterialTheme.colorScheme.onBackground,
            textStyle = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onError)
        )

        Spacer(modifier = Modifier.size(MaterialTheme.spacing.small))

        if (isActionRequired) {
            SimpleButton(action.title) {
                block()
            }
        }
    }
}

@Composable
@Preview
fun ActionStatePreview() {
    ActionStateView(
        action = ActionState.ERROR(),
        isActionRequired = false
    )
}
