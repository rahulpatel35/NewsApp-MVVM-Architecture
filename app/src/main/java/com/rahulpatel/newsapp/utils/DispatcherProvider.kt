package com.rahulpatel.newsapp.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

// DispatcherProvider is an abstraction over Dispatchers in Kotlin Coroutines.
// DefaultDispatcherProvider just delegates to the real Dispatchers provided by Kotlin.
// Why this is useful -> Dependency Injection, Testability, Consistency
// Dependency Injection : Instead of calling Dispatchers.IO or Dispatchers.Main directly inside your code, you inject a DispatcherProvider.
// This makes your code more flexible and testable.

// Testability-> In tests, you can provide a fake dispatcher (like TestCoroutineDispatcher or StandardTestDispatcher) so that your code runs synchronously and is easier to test.
// Consistency -> If later you want to change dispatchers (e.g., use a custom thread pool), you only modify DefaultDispatcherProvider instead of hunting down Dispatchers.IO everywhere.

// Where you typically use this pattern
// Repositories → network/db calls run on io
// UseCases → background work with default
// ViewModels → UI updates with main
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