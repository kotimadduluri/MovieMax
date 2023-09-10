package com.moviemax.common

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.moviemax.MainActivity
import com.moviemax.util.KoinTestRule
import org.junit.Rule
import org.koin.core.module.Module

open class BaseUITest(
    koinModules: List<Module> = emptyList()
) {

    @get:Rule
    val koinTestRule = KoinTestRule(
        modules = koinModules
    )

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
}