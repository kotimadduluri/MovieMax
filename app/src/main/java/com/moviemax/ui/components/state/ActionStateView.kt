package com.moviemax.ui.components.state

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moviemax.ui.components.button.SimpleButton

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
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = action.icon), // Replace with your error image resource
            contentDescription = "Error Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "Error",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onError),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(10.dp))
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
    ) {
        //
    }
}
