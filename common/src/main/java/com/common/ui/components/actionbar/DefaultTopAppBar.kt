package com.common.ui.components.actionbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.common.util.UiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppBar(
    title: UiText,
    scrollBehaviour: TopAppBarScrollBehavior,
    navigationIcon: @Composable () -> Unit = { DefaultNavigationIcon {} },
    toolbarActions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                text = title.asString(),
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = navigationIcon,
        actions = toolbarActions,
        scrollBehavior = scrollBehaviour
    )
}