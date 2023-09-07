package com.moviemax.ui.components.state

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moviemax.ui.components.text.TextView
import com.moviemax.R
import com.moviemax.ui.components.card.CardView

@Composable
fun ActionStateViewCard(
    action: ActionState,
    isActionRequired: Boolean = false,
    isActionIcon: Int = R.drawable.ic_refresh,
    modifier: Modifier = Modifier.padding(8.dp),
    block: () -> Unit
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
                    painter = painterResource(id = action.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp)
                        .align(Alignment.CenterVertically)
                )
            }

            TextView(
                text = action.message,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isActionRequired) {
                Image(
                    painter = painterResource(id = isActionIcon),
                    contentDescription = action.title,
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
        action = ActionState.LOADING(title = "Try"),
        isActionRequired = true
    ) {

    }
}