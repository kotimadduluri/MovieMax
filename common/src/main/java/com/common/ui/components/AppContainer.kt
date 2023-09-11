package com.common.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.common.R
import com.common.ui.components.actionbar.DefaultNavigationIcon
import com.common.ui.components.actionbar.DefaultTopAppBar
import com.common.util.UiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContainer(
    title: UiText = UiText.StringResource(R.string.back),
    scrollBehaviour: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    navigation: @Composable () -> Unit = { DefaultNavigationIcon {} },
    toolbarActions: @Composable RowScope.() -> Unit = {},
    toolbar: @Composable () -> Unit = {
        DefaultTopAppBar(
            title = title,
            scrollBehaviour = scrollBehaviour,
            navigationIcon = navigation,
            toolbarActions = toolbarActions
        )
    },
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = toolbar
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}
