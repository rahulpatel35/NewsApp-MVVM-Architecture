package com.rahulpatel.newsapp.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
//TestDispatcherProvider ensures all coroutines run immediately and safely during unit tests.
//This class provides test-friendly coroutine dispatchers.
/*What it does:
Uses UnconfinedTestDispatcher for all dispatchers
main
io
default
Replaces real dispatchers (Dispatchers.Main, IO, etc.) in unit tests*/

/*Why it’s needed:
Makes coroutine tests:
Deterministic
Fast
No Android dependency
No threading issues*/
@ExperimentalCoroutinesApi
class TestDispatcherProvider : DispatcherProvider {

    private val testDispatcher = UnconfinedTestDispatcher()

    override val main: CoroutineDispatcher
        get() = testDispatcher

    override val io: CoroutineDispatcher
        get() = testDispatcher

    override val default: CoroutineDispatcher
        get() = testDispatcher
}