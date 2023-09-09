package com.moviemax.common

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.network.reader.NetworkReader
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
abstract class BaseTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var networkReader: NetworkReader

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(TestDispatcherProvider().io) //
        initRequiredDependencies()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }


    abstract fun initRequiredDependencies()
}