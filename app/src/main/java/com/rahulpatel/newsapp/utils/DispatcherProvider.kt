package com.rahulpatel.newsapp.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

// this is a DispatcherProvider pattern for coroutines.
// if You hardcoded Dispatchers.IO.
//When running unit tests, coroutines will use the real IO dispatcher, which means:
//Code runs on background threads → tests can be slow, flaky, or hard to control.
//You can’t easily advance or pause coroutine execution in tests.

// What this achieves (the abstraction part)
//DispatcherProvider = abstraction (just defines what dispatchers you need, not which ones).
//DefaultDispatcherProvider = real implementation (production dispatchers).
//TestDispatcherProvider = fake implementation (test dispatchers).
//So your code depends on the abstraction (DispatcherProvider) instead of the concrete thing (Dispatchers.IO).
//This follows the Dependency Inversion Principle (DIP) from SOLID.

// In simple words
//Without abstraction → your code is locked to real dispatchers → hard to test.
//With abstraction (DispatcherProvider) → your code doesn’t care which dispatcher it’s running on → you can swap in real ones for production, fake ones for tests.
//This is why abstraction makes coroutine-based code cleaner, testable, and maintainable.

interface DispatcherProvider {

    val main: CoroutineDispatcher

    val io: CoroutineDispatcher

    val default: CoroutineDispatcher

}

class DefaultDispatcherProvider : DispatcherProvider {

    override val main: CoroutineDispatcher
        get() = Dispatchers.Main

    override val io: CoroutineDispatcher
        get() = Dispatchers.IO

    override val default: CoroutineDispatcher
        get() = Dispatchers.Default

}