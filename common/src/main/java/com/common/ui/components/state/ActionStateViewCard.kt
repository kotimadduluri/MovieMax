package com.common.ui.components.state

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.common.R
import com.common.ui.components.text.TextView
import com.common.ui.components.card.CardView
import com.common.ui.theme.spacing
import com.common.util.UiImage

@Composable
fun ActionStateViewCard(
    action: ActionState,
    isActionRequired: Boolean = false,
    isActionIcon: UiImage = UiImage.DrawableResource(R.drawable.ic_refresh),
    modifier: Modifier = Modifier.padding(8.dp),
    block: () -> Unit = {}
) {
    CardView(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (action is ActionState.LOADING) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp)
                        .align(Alignment.CenterVertically),
                    color = Color.Gray
                )
            } else {
                Image(
                    painter = action.icon.asPainter(),
                    contentDescription = action.title.asString(),
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp)
                        .align(Alignment.CenterVertically)
                )
            }

            TextView(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)
                    .padding(8.dp),
                text = action.message
            )

            Spacer(modifier = Modifier.size(MaterialTheme.spacing.medium))

            if (isActionRequired) {
                Image(
                    painter = isActionIcon.asPainter(),
                    contentDescription = action.title.asString(),
                    modifier = Modifier
                        .size(50.dp)
                        .padding(8.dp)
                        .align(alignment = Alignment.CenterVertically)
                        .clickable {
                            block()
                        },
                    alignment = Alignment.CenterEnd
                )
            }
        }
    }
}

@Preview
@Composable
fun ActionStateViewPreview() {
    ActionStateView(
        action = ActionState.LOADING(),
        isActionRequired = true
    )
}