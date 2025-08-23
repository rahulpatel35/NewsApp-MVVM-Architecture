package com.rahulpatel.newsapp.utils
//this is an abstraction for checking internet connectivity.
//Why make it an interface?
//Abstraction → your app depends on NetworkHelper (the contract), not on a specific implementation.
//You can have:
//A real implementation (using Android’s ConnectivityManager) for production.
//A fake/mock implementation for unit tests (so tests don’t rely on actual internet).
//This makes your code more testable and maintainable.
//NetworkHelper = abstraction for checking network connectivity.
//Makes it easy to swap implementations → real one in production, fake/mock in tests.
//Follows Dependency Inversion Principle (your code depends on the abstraction, not the platform-specific implementation).

interface NetworkHelper {
    fun isNetworkConnected(): Boolean
}