package com.common.ui.components.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun <T> HorizontalList(
    data: List<T>,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
    content: @Composable (index: Int, T) -> Unit
) {
    LazyRow(
        modifier = modifier
            .testTag("HorizontalList")
    ) {
        itemsIndexed(data) { index, item ->
            content(index, item)
        }
    }
}