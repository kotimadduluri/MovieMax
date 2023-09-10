package com.moviemax.ui.components.list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> VerticalList(
    data: List<T>,
    modifier: Modifier = Modifier.padding(8.dp),
    itemContent: @Composable (T) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(data) { item ->
            itemContent(item)
        }
    }
}