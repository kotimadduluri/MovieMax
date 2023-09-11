package com.common.ui.components.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.common.ui.theme.spacing

@Composable
fun CardView(
    modifier: Modifier = Modifier,
    onCardItemClicked: (() -> Unit)? = null,
    itemContent: @Composable () -> Unit
) {
    Column {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            modifier = modifier.padding(MaterialTheme.spacing.small)
        ) {
            Column(
                modifier = modifier.padding(MaterialTheme.spacing.small)
            ) {
                itemContent()
            }
        }
    }
}