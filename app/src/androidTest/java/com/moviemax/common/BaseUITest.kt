package com.moviemax.common

import android.content.Context
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.moviemax.MainActivity
import com.moviemax.util.KoinTestRule
import org.junit.Rule
import org.junit.rules.TestName
import org.koin.core.module.Module
import org.koin.test.KoinTest

open class BaseUITest(
    koinModules: List<Module> = emptyList()
) : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule(
        modules = koinModules,
        appContext = targetContext.applicationContext
    )

    @get:Rule
    var testName:TestName = TestName()

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val targetContext : Context
        get() = InstrumentationRegistry.getInstrumentation().targetContext
}