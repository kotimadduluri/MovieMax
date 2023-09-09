package com.moviemax.ui.components.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
            modifier = modifier.padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                itemContent()
            }
        }
    }
}